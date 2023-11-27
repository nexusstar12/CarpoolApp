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

    public CityValidationEnum cityInputValidation(String city) {
        if(city == null || city.isBlank()) {
            return CityValidationEnum.CITY_HAS_BLANK_NAME;
        } else if (!city.matches("^[a-zA-Z\\- ]+$")) { // Allows alphabetic chars, hyphens, and spaces
            return CityValidationEnum.CITY_NAME_CONTAINS_NON_ALPHA_CHARS;
        } else if (city.length() > 85) {
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
        StringBuilder errors = new StringBuilder();

        // Length validation for street address and city
        validateLength("startStreet", poolData, 85, "invalidEntry.streetAddressMustConsistOfFewerThan86Characters", errors);
        validateLength("endStreet", poolData, 85, "invalidEntry.streetAddressMustConsistOfFewerThan86Characters", errors);
        validateLength("startCity", poolData, 85, "invalidEntry.cityMustConsistOfFewerThan86Characters", errors);
        validateLength("endCity", poolData, 85, "invalidEntry.cityMustConsistOfFewerThan86Characters", errors);

        // Length validation for zip code
        validateLength("startZip", poolData, 5, "invalidEntry.zipMustBeFiveCharacters", errors);
        validateLength("endZip", poolData, 5, "invalidEntry.zipMustBeFiveCharacters", errors);

        // Length validation for state
        validateLength("startState", poolData, 2, "invalidEntry.stateMustBeTwoCharacters", errors);
        validateLength("endState", poolData, 2, "invalidEntry.stateMustBeTwoCharacters", errors);

        // Character validation for street address
        validateCharacters("startStreet", poolData, "^[a-zA-Z0-9-. ]+$", "invalidEntry.streetAddressMustContainOnlyLettersHyphensPeriods", errors);
        validateCharacters("endStreet", poolData, "^[a-zA-Z0-9-. ]+$", "invalidEntry.streetAddressMustContainOnlyLettersHyphensPeriods", errors);


        // Character validation for city
        validateCharacters("startCity", poolData, "^[a-zA-Z-. ]+$", "invalidEntry.cityMustContainOnlyLettersHyphensPeriods", errors);
        validateCharacters("endCity", poolData, "^[a-zA-Z-. ]+$", "invalidEntry.cityMustContainOnlyLettersHyphensPeriods", errors);

        // Character validation for zip code
        validateCharacters("startZip", poolData, "^[0-9]+$", "invalidEntry.zipMustBeNumeric", errors);
        validateCharacters("endZip", poolData, "^[0-9]+$", "invalidEntry.zipMustBeNumeric", errors);

        // Character validation for state
        validateCharacters("startState", poolData, "^[a-zA-Z]+$", "invalidEntry.stateMustBeAlphabetic", errors);
        validateCharacters("endState", poolData, "^[a-zA-Z]+$", "invalidEntry.stateMustBeAlphabetic", errors);

        // Name or description validation
        if (poolData.get("name") == null || ((String) poolData.get("name")).isBlank()) {
            errors.append("error.descriptionCannotBeNull ");
        }

        // startStreet validation
        if (poolData.get("startStreet") == null || ((String) poolData.get("startStreet")).isBlank()) {
            errors.append("error.startStreetCannotBeNull ");
        }

        // startCity validation
        if (poolData.get("startCity") == null || ((String) poolData.get("startCity")).isBlank()) {
            errors.append("error.startCityCannotBeNull ");
        }

        // startZip validation
        if (poolData.get("startZip") == null || ((String) poolData.get("startZip")).isBlank()) {
            errors.append("error.startZipCannotBeNull ");
        }

        // startState validation
        if (poolData.get("startState") == null || ((String) poolData.get("startState")).isBlank()) {
            errors.append("error.startStateCannotBeNull ");
        }

        // endStreet validation
        if (poolData.get("endStreet") == null || ((String) poolData.get("endStreet")).isBlank()) {
            errors.append("error.endStreetCannotBeNull ");
        }

        // endCity validation
        if (poolData.get("endCity") == null || ((String) poolData.get("endCity")).isBlank()) {
            errors.append("error.endCityCannotBeNull ");
        }

        // endZip validation
        if (poolData.get("endZip") == null || ((String) poolData.get("endZip")).isBlank()) {
            errors.append("error.endZipCannotBeNull ");
        }

        // endState validation
        if (poolData.get("endState") == null || ((String) poolData.get("endState")).isBlank()) {
            errors.append("error.endStateCannotBeNull ");
        }

        // Privacy validation
        if (poolData.get("privacy") == null) {
            errors.append("error.privacyCannotBeNull ");
        }

        // Character validation for street address to contain at least one letter
        if (poolData.get("startStreet") != null && !containsLetter((String) poolData.get("startStreet"))) {
            errors.append("error.startStreetMustContainAtLeastOneLetter ");
        }
        if (poolData.get("endStreet") != null && !containsLetter((String) poolData.get("endStreet"))) {
            errors.append("error.endStreetMustContainAtLeastOneLetter ");
        }

        // Character validation for city to contain at least one letter
        if (poolData.get("startCity") != null && !containsLetter((String) poolData.get("startCity"))) {
            errors.append("error.startCityMustContainAtLeastOneLetter ");
        }
        if (poolData.get("endCity") != null && !containsLetter((String) poolData.get("endCity"))) {
            errors.append("error.endCityMustContainAtLeastOneLetter ");
        }

        // Check for startDateTime and validate it's in the future
        String startDateTimeStr = (String) poolData.get("startTime");
        if (startDateTimeStr != null && !startDateTimeStr.isBlank()) {
            try {
                LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr);
                if (startDateTime.isBefore(LocalDateTime.now())) {
                    errors.append("invalidEntry.dateTimeMustBeInTheFuture ");
                }
            } catch (DateTimeParseException e) {
                errors.append("invalid.startTimeFormat ");
            }
        }

        // Return any errors here
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors.toString().trim());
        }

        // If all fields are valid
        return ResponseEntity.ok("All fields are valid");
    }

    // Check if a string contains at least one letter
    private boolean containsLetter(String input) {
        return input.matches(".*[a-zA-Z]+.*");
    }

    private void validateLength(String fieldName, Map<String, Object> poolData, int maxLength, String errorMessage, StringBuilder errors) {
        String fieldValue = (String) poolData.get(fieldName);
        if (fieldValue != null && fieldValue.length() > maxLength) {
            errors.append(errorMessage).append(" ");
        }
    }

    private void validateCharacters(String fieldName, Map<String, Object> poolData, String regex, String errorMessage, StringBuilder errors) {
        String fieldValue = (String) poolData.get(fieldName);
        if (fieldValue != null && !fieldValue.matches(regex)) {
            errors.append(errorMessage).append(" ");
        }
    }
}
