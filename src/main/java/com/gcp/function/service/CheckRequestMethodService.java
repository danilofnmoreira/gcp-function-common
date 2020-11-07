package com.gcp.function.service;

import java.util.Arrays;

import com.google.cloud.functions.HttpRequest;

import org.springframework.http.HttpMethod;
import org.springframework.web.server.MethodNotAllowedException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckRequestMethodService {

    public static void checkMethod(HttpRequest request, HttpMethod... allowedMethods) {

        var requestMethod = HttpMethod.resolve(request.getMethod());

        for (var method : allowedMethods) {
            if (method.equals(requestMethod)) {
                return;
            }
        }

        throw new MethodNotAllowedException(requestMethod, Arrays.asList(allowedMethods));
    }

    public static void checkGetMethod(HttpRequest request) {

        checkMethod(request, HttpMethod.GET);
    }

    public static void checkPostMethod(HttpRequest request) {

        checkMethod(request, HttpMethod.POST);
    }

    public static void checkDeleteMethod(HttpRequest request) {

        checkMethod(request, HttpMethod.DELETE);
    }

    public static void checkPutMethod(HttpRequest request) {

        checkMethod(request, HttpMethod.PUT);
    }

}
