package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SignUpValidationResponseStringGenerator {

    public static String generateResponse(Map<String, String> jsonMap, ValidationService validationService) {
        SignUpValidationEnum signUpValidationEnum;
        for (String key : jsonMap.keySet()) {
            signUpValidationEnum = validationService.signUpValidation(jsonMap.get(key));
            if (signUpValidationEnum == SignUpValidationEnum.IS_EMPTY) {
                return key + SignUpValidationEnum.IS_EMPTY;
            }
        }

        return SignUpValidationEnum.IS_VALID.toString();
    }


}
