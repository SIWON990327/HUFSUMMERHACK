package com.example.hack.service;

import com.example.hack.entity.University;
import com.example.hack.entity.RestrantEntity;
import com.example.hack.repository.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
    
    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public University createUniversity(String univname, double latitude, double longitude) {
        University university = new University();
        university.setUnivname(univname);
        university.setLatitude(latitude);
        university.setLongitude(longitude);
        return universityRepository.save(university);
    }

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public University getUniversityByName(String univname) {
        return universityRepository.findByUnivname(univname)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public void deleteUniversityById(Long univid) {
        universityRepository.deleteById(univid);
    }

    public University updateUniversity(University updateUniversity) {
        return universityRepository.save(updateUniversity);
    }

    public void addRestaurantToUniversity(Long univid, Restaurant restaurant) {
        University university = universityRepository.findById(univid).orElse(null);
        if (university != null) {
            university.getRestaurants().add(restaurant);
            restaurant.setUniversity(university);
            universityRepository.save(university);
        }




}
