package com.bikkadit.electoronic.store.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidation implements ConstraintValidator<ImageNameValid,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return !value.isBlank();
    }
}
