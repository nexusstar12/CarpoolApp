package com.fantasticfour.poolapp.services;

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
}
