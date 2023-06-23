package com.example.hack.controller;

import com.example.hack.entity.University;
import com.example.hack.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/university")
@RestController
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    @PostMapping("/create")
    public ResponseEntity<University> createUniversity(@RequestBody University university){
        University createdUniversity = universityService.createUniversity(university.getUnivname(),
                university.getLatitude(), university.getLongitude());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUniversity);
    }

}
