package com.example.front.controller;

import com.example.front.model.*;
import com.example.front.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // =================================================
    // 🔥 1. 註冊 API
    // =================================================
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Student student) {

        // ❌ Email 重複
        if (studentRepository.existsByEmail(student.getEmail())) {
            return Map.of(
                    "success", false,
                    "message", "Email 已被註冊"
            );
        }

        try {
            // 🔐 密碼加密
            student.setPassword(encoder.encode(student.getPassword()));

            // 💾 存入資料庫
            studentRepository.save(student);

            return Map.of(
                    "success", true,
                    "message", "註冊成功"
            );

        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "註冊失敗"
            );
        }
    }

    // =================================================
    // 🔥 2. 國籍 API（AngularJS 下拉選單）
    // =================================================
    @GetMapping("/countries")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    // =================================================
    // 🔥 3. 登入 API（AngularJS 使用）
    // =================================================
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {

        String email = req.get("email");
        String password = req.get("password");

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return Map.of(
                    "success", false,
                    "message", "帳號不存在"
            );
        }

        if (!encoder.matches(password, student.getPassword())) {
            return Map.of(
                    "success", false,
                    "message", "密碼錯誤"
            );
        }

        return Map.of(
                "success", true,
                "message", "登入成功",
                "user", student
        );
    }

    // =================================================
    // 🔥 4. Profile API（之後 AngularJS dashboard 用）
    // =================================================
    @GetMapping("/profile/{email}")
    public Map<String, Object> getProfile(@PathVariable String email) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return Map.of(
                    "success", false,
                    "message", "查無學生"
            );
        }

        // =============================
        // 1. 取預約
        // =============================
        List<Appointments> appointmentsList =
                appointmentsRepository.findByEmail(email);

        // =============================
        // 2. 訂單
        // =============================
        List<Orders> ordersList =
                ordersRepository.findByStudentId(student.getId());

        // =============================
        // 3. 課程
        // =============================
        List<Integer> courseIds = ordersList.stream()
                .map(Orders::getCourseId)
                .toList();

        List<Courses> coursesList =
                coursesRepository.findByIdIn(courseIds);

        Map<Integer, Courses> courseMap = coursesList.stream()
                .collect(Collectors.toMap(Courses::getId, c -> c));

        // =============================
        // 4. 組合 CourseOrder
        // =============================
        List<Map<String, Object>> courseOrders = new ArrayList<>();

        for (Orders order : ordersList) {

            Courses course = courseMap.get(order.getCourseId());
            if (course == null) continue;

            Map<String, Object> obj = new HashMap<>();
            obj.put("courseName", course.getCourseName());
            obj.put("startDate", course.getStartDate());
            obj.put("endDate", course.getEndDate());
            obj.put("durationDays",
                    ChronoUnit.DAYS.between(course.getStartDate(), course.getEndDate()) + 1);
            obj.put("status", order.getOrderStatus());

            courseOrders.add(obj);
        }

        // =============================
        // 回傳
        // =============================
        return Map.of(
                "success", true,
                "student", student,
                "appointments", appointmentsList,
                "courseOrders", courseOrders
        );
    }
}