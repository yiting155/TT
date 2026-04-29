package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CountryRepository countryRepository;

    // 👉 顯示註冊頁
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("student", new Student());

        // 🔥 把國家資料丟到前端
        model.addAttribute("countries", countryRepository.findAll());

        return "register";
    }

    // 👉 註冊
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Student student, Model model) {

        // 🔴 Email 重複檢查
        if (studentRepository.existsByEmail(student.getEmail())) {
            model.addAttribute("error", "Email 已被註冊");
            model.addAttribute("countries", countryRepository.findAll());
            return "register";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();    //加密的方法
        String hash = encoder.encode(student.getPassword());   //撈出密碼做加密動作
        student.setPassword(hash);     //取得已密的密碼

        studentRepository.save(student);      //將密碼存回資料庫中

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }


    @PostMapping("/login")
    public String login(Model model, String email, String password) {

        Student student = studentRepository.findByEmail(email);
        String hash = student.getPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();    //驗證的方法 BCryptPasswordEncoder-->這個包含加密跟驗證的方式
        boolean loginResult = encoder.matches(password, hash);

        return "redirect:/loginFailed";
    }
}