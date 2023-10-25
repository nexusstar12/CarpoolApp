package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.ZipData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipDataRepository extends JpaRepository<ZipData, String> {
}
