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

    private void updateUniversityLocation(University university) {
        String universityName = university.getUnivname();
        // NaverMapApiClient를 통해 대학의 위치 정보를 가져옵니다
        double[] location = naverMapApiClient.getUniversityLocation(universityName);
        if (location != null && location.length == 2) {
            double latitude = location[0];
            double longitude = location[1];
            university.setLatitude(latitude);
            university.setLongitude(longitude);
        } else {
            throw new RuntimeException("Failed to retrieve university location");
        }
    }
    public University createUniversity(String name, double latitude, double longitude) {
        University university = new University();
        university.setUnivname(name);
        university.setLatitude(latitude);
        university.setLongitude(longitude);
        // 대학 위치 업데이트
        updateUniversityLocation(university);
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
