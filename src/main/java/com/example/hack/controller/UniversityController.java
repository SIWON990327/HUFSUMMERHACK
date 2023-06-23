package com.example.hack.controller;

import com.example.hack.entity.University;
import com.example.hack.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        University createdUniversity = universityService.createUniversity(university.getUnivname(), university.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUniversity);
    }

}
