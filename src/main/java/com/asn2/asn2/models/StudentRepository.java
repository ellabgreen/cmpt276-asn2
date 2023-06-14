package com.asn2.asn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    //find
    List<Student> findByName(String name);
    Student findByUid(int uid);
    //delete
    long deleteByUid(int uid);
}
