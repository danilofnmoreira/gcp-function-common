package com.gcp.function.service;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import com.gcp.function.config.ObjectMapperConfig;
import com.gcp.function.payload.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseService {

    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperConfig.OBJECT_MAPPER;

    public static void answerMethodNotAllowed(HttpRequest request, HttpResponse response, MethodNotAllowedException e) {

        var status = HttpStatus.METHOD_NOT_ALLOWED;

        fillResponseWithStatus(response, status);

        var error = createErrorResponse(request, status);
        error.setMessage(e.getMessage() + ". Allowed methods are " + e.getSupportedMethods());

        writeError(response, error);
    }

    public static void answerBadRequest(HttpRequest request, HttpResponse response,
            UnsupportedMediaTypeStatusException e) {

        var status = HttpStatus.BAD_REQUEST;

        fillResponseWithStatus(response, status);

        var error = createErrorResponse(request, status);
        error.setMessage(e.getMessage() + ". Allowed Content-Type are " + e.getSupportedMediaTypes());

        writeError(response, error);
    }

    public static void answerInternalServerError(HttpRequest request, HttpResponse response, Exception e) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        fillResponseWithStatus(response, status);

        var error = createErrorResponse(request, status);
        error.setMessage(e.getMessage());

        writeError(response, error);
    }

    public static void answerBadRequest(HttpRequest request, HttpResponse response, HttpMessageNotReadableException e) {

        var status = HttpStatus.BAD_REQUEST;

        fillResponseWithStatus(response, status);

        var error = createErrorResponse(request, status);
        error.setMessage(e.getMessage());

        writeError(response, error);
    }

    public static void answerBadRequest(HttpRequest request, HttpResponse response, ConstraintViolationException e) {

        var status = HttpStatus.BAD_REQUEST;

        fillResponseWithStatus(response, status);

        var error = createErrorResponse(request, status);
        error.setMessage(e.getMessage());

        var errors = new HashSet<Map<String, Object>>();
        e.getConstraintViolations().forEach(c -> {
            var map = new HashMap<String, Object>();
            c.getInvalidValue();
            c.getMessage();
            c.getPropertyPath();
            map.put("error_message", c.getMessage());
            map.put("rejected_value", c.getInvalidValue());
            map.put("field", StreamSupport.stream(c.getPropertyPath().spliterator(), false).reduce((first, second) -> second).get().getName());
            errors.add(map);
        });

        error.setErrors(errors);

        writeError(response, error);
    }

    @SneakyThrows
    private static void writeError(HttpResponse response, ErrorResponse error) {

        var writer = new PrintWriter(response.getWriter());

        writer.print(OBJECT_MAPPER.writeValueAsString(error));
    }

    private static void fillResponseWithStatus(HttpResponse response, HttpStatus status) {

        response.setStatusCode(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private static ErrorResponse createErrorResponse(HttpRequest request, HttpStatus status) {

        return ErrorResponse
            .builder()
            .timestamp(ZonedDateTime.now())
            .status(status.value())
            .statusError(status.getReasonPhrase())
            .path(request.getPath())
            .build();
    }

}
