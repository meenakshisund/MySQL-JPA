package com.mysql.jpa.controller;

import com.mysql.jpa.entity.StudentEntity;
import com.mysql.jpa.exception.ResourceNotFoundException;
import com.mysql.jpa.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
class StudentController {

    @Autowired
    private StudentRepo repo;

    @GetMapping(value = "/students")
    public List<StudentEntity> getAllStudents() {
        return repo.findAll();
    }

    @GetMapping(value = "/student/{id}")
    public StudentEntity getStudentsById(@PathVariable("id") Long id) {
        Optional<StudentEntity> student = repo.findById(id);
        return student.orElse(null);
    }

    @PostMapping(value = "/student")
    public StudentEntity createStudent(@RequestBody StudentEntity studentEntity) {
        return repo.save(studentEntity);
    }

    @PutMapping(value = "/student")
    public StudentEntity updateStudent(@RequestBody StudentEntity studentEntity) throws ResourceNotFoundException {
        Optional<StudentEntity> student = repo.findById(studentEntity.getId());
        if(student.isPresent()) {
            StudentEntity studentFromDb = student.get();

            studentFromDb.setFirstName(studentEntity.getFirstName());
            studentFromDb.setLastName(studentEntity.getLastName());
            studentFromDb.setEmailAddress(studentEntity.getEmailAddress());

            return repo.save(studentFromDb);
        } else
            throw new ResourceNotFoundException("Student "  + studentEntity.getId() +" not Found");
    }

    @DeleteMapping(value = "/student")
    public void deleteStudent(@RequestBody StudentEntity studentEntity) throws ResourceNotFoundException {
        Optional<StudentEntity> student = repo.findById(studentEntity.getId());
        if(student.isPresent()) {
            StudentEntity studentFromDb = student.get();
            repo.deleteById(studentFromDb.getId());
        } else
            throw new ResourceNotFoundException("Student "  + studentEntity.getId() +" not Found");
    }
}