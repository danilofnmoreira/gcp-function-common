package com.gcp.function.service;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.google.cloud.functions.HttpRequest;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckRequestContentTypeService {

    public static void checkContentType(HttpRequest request, MediaType... allowedContentTypes) {

        try {

            var requestContentType = MediaType.parseMediaType(request.getContentType().get());

            for (var contentType : allowedContentTypes) {
                if (contentType.equals(requestContentType)) {
                    return;
                }
            }

            throw new UnsupportedMediaTypeStatusException(requestContentType, Arrays.asList(allowedContentTypes));
        } catch (NoSuchElementException e) {

            throw new UnsupportedMediaTypeStatusException(null, Arrays.asList(allowedContentTypes));
        } catch (InvalidMediaTypeException e) {

            throw new UnsupportedMediaTypeStatusException(e.getMessage());
        }
    }

    public static void checkApplicationJsonContentType(HttpRequest request) {

        checkContentType(request, MediaType.APPLICATION_JSON);
    }

}
