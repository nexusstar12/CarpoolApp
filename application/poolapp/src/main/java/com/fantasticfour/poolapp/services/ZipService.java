package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.CustomResponse.ZipCodeValidationEnum;
import com.fantasticfour.poolapp.domain.ZipData;
import com.fantasticfour.poolapp.repository.ZipDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ZipService {
    @Autowired
    private ZipDataRepository zipDataRepository;

    public Optional<ZipData> getZipData (String zip) {
       return zipDataRepository.findById(zip);
    }

    public ZipCodeValidationEnum zipcodeValidation (String zipcode) {
        System.out.println("zip validaztion, in zip service: " + zipcode);
        if (zipcode == null || zipcode.isBlank()) {
            return ZipCodeValidationEnum.BLANK_ZIP;
        }else if (!zipcode.matches("^[0-9]+$")) {
            return ZipCodeValidationEnum.HAS_NON_NUMERIC_CHAR;
        } else if (zipcode.length() != 5) {
            return ZipCodeValidationEnum.ZIP_DOES_NOT_HAVE_CORRECT_LENGTH;
        }

        return ZipCodeValidationEnum.VALID_ZIP;
    }


}
