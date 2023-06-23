package com.example.hack.service;

import com.example.hack.entity.University;
import com.example.hack.repository.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final NaverMapApiClient naverMapApiClient;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, NaverMapApiClient naverMapApiClient) {
        this.universityRepository = universityRepository;
        this.naverMapApiClient = naverMapApiClient;
    }

    public University createUniversity(String name, double latitude, double longitude) {
        University university = new University();
        university.setUnivname(name);
        university.setLatitude(latitude);
        university.setLongitude(longitude);


        return universityRepository.save(university);
    }

    public University getUniversityByName(String univname) {
        return universityRepository.findByUnivname(univname)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public void deleteUniversityByName(String univname) {
        Optional<University> universityOptional = universityRepository.findByUnivname(univname);
        if(universityOptional.isPresent()){
            University university = universityOptional.get();
            universityRepository.delete(university);
        }
    }

    public University updateUniversity(University updateUniversity) {
        return universityRepository.save(updateUniversity);
    }


}
