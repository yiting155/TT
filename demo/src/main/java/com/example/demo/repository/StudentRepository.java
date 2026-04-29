package com.example.demo.repository;

import com.example.demo.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);
    Student findByEmail(String email);
}

//只要繼承 JpaRepository，你就直接有：
//save(employee)        // 新增或更新
//findById(id)          // 查一筆
//findAll()             // 查全部
//deleteById(id)        // 刪除
//count()               // 計數
