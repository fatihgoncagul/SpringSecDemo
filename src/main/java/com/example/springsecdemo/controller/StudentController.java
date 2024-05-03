package com.example.springsecdemo.controller;

import com.example.springsecdemo.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>(List.of(
            new Student(1,"navin","java"),
            new Student(2,"kiran","blockchain")
    ));


    @GetMapping("students")
    public List<Student> getStudents(){
        return students;
    }
    @PostMapping("students")
    public void addStudent(@RequestBody Student student){
        students.add(student);

    }

}
