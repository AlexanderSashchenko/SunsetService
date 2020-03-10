package com.datax.sunset.controller.validation;

import com.datax.sunset.service.CityService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCityNameValidator implements ConstraintValidator<UniqueCityName, String> {

    @Autowired
    private CityService cityService;

    @Override
    public boolean isValid(String cityName,
                           ConstraintValidatorContext constraintValidatorContext) {
        return cityService.findByName(cityName).isEmpty();
    }
}
