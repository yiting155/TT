package com.example.demo.model;

import com.example.demo.constant.StudentGender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "uuid", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String uuid = java.util.UUID.randomUUID().toString();
//    @PrePersist
//    public void generateUUID() {
//        if (this.uuid == null) {
//            this.uuid = java.util.UUID.randomUUID().toString();
//        }
//    }

//    @Column(name = "uuid", unique = true, nullable = false)
//    private String uuid;


    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birthday")
    private LocalDate birthday;

//    @Column(name = "gender")
//    private String gender;

    @Enumerated(EnumType.STRING)
    private StudentGender gender;

//    @ManyToOne
//    @JoinColumn(name = "country_id")
//    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "ice_contact")
    private String iceContact;

    @Column(name = "ice_phone")
    private String icePhone;

    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.time.LocalDateTime createdAt;
}