package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    // 👉 顯示註冊頁
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());

        // 🔥 把國家資料丟到前端
        model.addAttribute("countries", countryRepository.findAll());

        return "register";
    }

    // 👉 註冊
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        // 🔴 Email 重複檢查
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email 已被註冊");
            model.addAttribute("countries", countryRepository.findAll());
            return "register";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();    //加密的方法
        String hash = encoder.encode(user.getPassword());   //撈出密碼做加密動作
        user.setPassword(hash);     //取得已密的密碼

        userRepository.save(user);      //將密碼存回資料庫中

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }


    @PostMapping("/login")
    public String login(Model model, String email, String password) {

        User user = userRepository.findByEmail(email);
        String hash = user.getPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();    //驗證的方法 BCryptPasswordEncoder-->這個包含加密跟驗證的方式
        boolean loginResult = encoder.matches(password, hash);

        return "redirect:/loginFailed";
    }
}