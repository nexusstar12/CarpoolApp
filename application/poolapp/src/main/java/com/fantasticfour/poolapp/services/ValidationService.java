package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.CustomResponse.CityValidationEnum;
import com.fantasticfour.poolapp.CustomResponse.SignUpValidationEnum;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public CityValidationEnum cityInputValidation (String city) {
        if(city == null || city.isBlank()) {
            return CityValidationEnum.CITY_HAS_BLANK_NAME;
        } else if (!city.matches("^[a-zA-Z ]+$")) { //contains non-alphabetic chars. spaces allowed
            return CityValidationEnum.CITY_NAME_CONTAINS_NON_ALPHA_CHARS;
        } else if (city.length() > 85 ) {
            return CityValidationEnum.CITY_NAME_TOO_LONG;
        }
        return CityValidationEnum.CITY_IS_VALID;
    }

    public SignUpValidationEnum signUpValidation (String signUpValue) {
        if (signUpValue == null || signUpValue.isBlank()) {
            return SignUpValidationEnum.IS_EMPTY;
        }
        return SignUpValidationEnum.IS_VALID;
    }
}
