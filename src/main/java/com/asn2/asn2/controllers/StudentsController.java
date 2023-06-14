package com.asn2.asn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asn2.asn2.models.Student;
import com.asn2.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Controller
public class StudentsController {

    @Autowired
    private StudentRepository studentRepo;


    //student list
    @GetMapping("/students/view")
    public String studentShow(Model model){
        System.out.println("Getting all students");
        // get all students from database
        List<Student> students = studentRepo.findAll();
        // end of database call
        model.addAttribute("st", students);
        return "students/showAll";
    }

    //student display
    @GetMapping("/students/display")
    public String studentDisplay(Model model){
        System.out.println("Getting all students");
        // get all students from database
        List<Student> students = studentRepo.findAll();
        // end of database call
        model.addAttribute("st", students);
        return "students/displayAll";
    }

    //form to edit new student
    @GetMapping("/students/view/{uid}")
    public String studentForm(Model model, @PathVariable Integer uid){
        
        System.out.println("GET User " + uid);
        // call db

        Student student = studentRepo.findByUid(uid);
   
        model.addAttribute("st", student);
        return "students/editStudent.html";
    }

    //delete student
    @Transactional
    @GetMapping("/students/view/{uid}/delete")
    public String deleteStudent(Model model, @PathVariable Integer uid){
        
        System.out.println("GET User " + uid);
        // call db

        studentRepo.deleteByUid(uid);

        return "students/addedStudent.html";
    }

    //edit student
    @PostMapping("/students/view/{uid}/edit")
    public String editStudent(@RequestParam Map<String, String> updatedstudent, HttpServletResponse response, @PathVariable Integer uid){
        System.out.println("GET student " + uid);
        Student student = studentRepo.findByUid(uid);

        student.setName(updatedstudent.get("name"));
        student.setWeight(updatedstudent.get("weight"));
        student.setHeight(updatedstudent.get("height"));
        student.setHair(updatedstudent.get("hair"));
        student.setGpa(updatedstudent.get("gpa"));

        studentRepo.save(student);

        return "students/addedStudent.html";
    }

    //add new student
    @PostMapping("students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response){
        System.out.println("ADD student");
        String newName = newstudent.get("name");
        String newWeight = newstudent.get("weight");
        String newHeight = newstudent.get("height");
        String newHair = newstudent.get("hair");
        String newGpa = newstudent.get("gpa");

        studentRepo.save(new Student(newName,newWeight,newHeight,newHair,newGpa));

        response.setStatus(201);

        return "students/addedStudent.html";
    }
}
