package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SignUpValidationResponseStringGenerator {

    public static String generateResponse(Map<String, String> jsonMap, ValidationService validationService) {
        SignUpValidationEnum signUpValidationEnum;

        // Extract the role from the map first, to adjust validations accordingly
        String role = jsonMap.getOrDefault("role", "passenger"); // Default to passenger if role isn't specified

        for (String key : jsonMap.keySet()) {
            // Skip validation for driver-specific fields if the role is not driver
            if (("fasTrakVerification".equals(key) || "driversLicense".equals(key)) && !"driver".equals(role)) {
                continue;
            }

            signUpValidationEnum = validationService.signUpValidation(key, jsonMap.get(key));
            if (signUpValidationEnum != SignUpValidationEnum.IS_VALID) {
                return key + "." + signUpValidationEnum;
            }
        }

        return SignUpValidationEnum.IS_VALID.toString();
    }
}
