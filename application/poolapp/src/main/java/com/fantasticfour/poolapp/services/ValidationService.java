package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.CustomResponse.CityValidationEnum;
import com.fantasticfour.poolapp.CustomResponse.SignUpValidationEnum;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

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

    public ResponseEntity<String> createPoolValidation(Map<String, Object> poolData) {
        // Check for null or empty values in description
        if (poolData.get("description") == null || ((String) poolData.get("description")).isBlank()) {
            return ResponseEntity.badRequest().body("required.descriptionCannotBeNull");
        }
        // Check for null or empty values in startStreet
        if (poolData.get("startStreet") == null || ((String) poolData.get("startStreet")).isBlank()) {
            return ResponseEntity.badRequest().body("required.startStreetCannotBeNull");
        }
        // Check for null or empty values in startCity
        if (poolData.get("startCity") == null || ((String) poolData.get("startCity")).isBlank()) {
            return ResponseEntity.badRequest().body("required.startCityCannotBeNull");
        }
        // Check for null or empty values in startZip
        if (poolData.get("startZip") == null || ((String) poolData.get("startZip")).isBlank()) {
            return ResponseEntity.badRequest().body("required.startZipCannotBeNull");
        }
        // Check for null or empty values in startState
        if (poolData.get("startState") == null || ((String) poolData.get("startState")).isBlank()) {
            return ResponseEntity.badRequest().body("required.startStateCannotBeNull");
        }
        // Check for null or empty values in endStreet
        if (poolData.get("endStreet") == null || ((String) poolData.get("endStreet")).isBlank()) {
            return ResponseEntity.badRequest().body("required.endStreetCannotBeNull");
        }
        // Check for null or empty values in endCity
        if (poolData.get("endCity") == null || ((String) poolData.get("endCity")).isBlank()) {
            return ResponseEntity.badRequest().body("required.endCityCannotBeNull");
        }
        // Check for null or empty values in endZip
        if (poolData.get("endZip") == null || ((String) poolData.get("endZip")).isBlank()) {
            return ResponseEntity.badRequest().body("required.endZipCannotBeNull");
        }
        // Check for null or empty values in endState
        if (poolData.get("endState") == null || ((String) poolData.get("endState")).isBlank()) {
            return ResponseEntity.badRequest().body("required.endStateCannotBeNull");
        }
        // Check for null or empty values in creatorId
        if (poolData.get("creatorId") == null) {
            return ResponseEntity.badRequest().body("required.creatorIdCannotBeNull");
        }
        // Check for null values in privacy
        if (poolData.get("privacy") == null) {
            return ResponseEntity.badRequest().body("required.privacyCannotBeNull");
        }
        // Check for null or improperly formatted values in startTime
        if (poolData.get("startTime") == null || ((String) poolData.get("startTime")).isBlank()) {
            return ResponseEntity.badRequest().body("required.startTimeCannotBeNull");
        } else {
            try {
                LocalDateTime.parse((String) poolData.get("startTime"));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("invalid.startTimeFormat");
            }
        }
        // If all fields are valid
        return ResponseEntity.ok("All fields are valid");
    }
}
