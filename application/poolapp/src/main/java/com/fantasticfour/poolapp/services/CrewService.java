package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.repository.CrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrewService {

    @Autowired
    private CrewRepository crewRepository;

    public Crew addCrew(Crew crew) {
        return crewRepository.save(crew);
    }

    public List<Optional<Crew>> getCrewByUserId(int id) {
        return crewRepository.findByUser_UserId(id);
    }

    public Optional<Crew> getCrewById(int id) {return crewRepository.findById(id);}

    public List<Crew> getAllCrews() {
        return crewRepository.findAll();
    }

    public Crew updateCrew(Crew crew) {
        if (crewRepository.existsById(crew.getCrewId())) {
            return crewRepository.save(crew);
        }
        return null;  // Alternatively, you can throw a custom exception or handle it in another way.
    }

    public void deleteCrew(int id) {
        crewRepository.deleteById(id);
    }

    public Crew getCrewbyPoolId(int poolId){
        return crewRepository.findByPool_PoolId(poolId);
    }

    public List<Crew> getCrewsByUserID (int userId) {
        return crewRepository.findByUser_UserId(userId).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
