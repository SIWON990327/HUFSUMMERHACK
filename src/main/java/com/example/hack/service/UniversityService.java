package com.example.hack.service;

import com.example.hack.entity.University;
import com.example.hack.repository.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final NaverMapService naverMapService;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, NaverMapService naverMapService) {
        this.universityRepository = universityRepository;
        this.naverMapService = naverMapService;
    }

    public University createUniversity(String name, double latitude, double longitude) {
        University university = new University();
        university.setName(name);
        university.setLatitude(latitude);
        university.setLongitude(longitude);

        naverMapService.saveRestaurantsWithinRadius(university);

        return universityRepository.save(university);
    }
    private static class UniversityApiResponse {
        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }


    public University getUniversityByName(String univname) {
        return universityRepository.findByUnivname(univname)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public void deleteUniversityByName(String univname) {
        universityRepository.deleteByName();
    }

    public University updateUniversity(University updateUniversity) {
        return universityRepository.save(updateUniversity);
    }


}
