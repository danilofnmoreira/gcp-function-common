package com.gcp.function.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorConfig {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    public static final Validator VALIDATOR = validatorFactory.getValidator();

}
