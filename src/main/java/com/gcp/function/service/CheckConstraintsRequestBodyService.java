package com.gcp.function.service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.util.CollectionUtils;

import com.gcp.function.config.ValidatorConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckConstraintsRequestBodyService {

    private static final Validator VALIDATOR = ValidatorConfig.VALIDATOR;

    public static <T> void checkConstraints(T body) {

        var violations = VALIDATOR.validate(body);

        if (CollectionUtils.isEmpty(violations)) {

            return;
        }

        throw new ConstraintViolationException("There are fields errors.", violations);
    }

}
