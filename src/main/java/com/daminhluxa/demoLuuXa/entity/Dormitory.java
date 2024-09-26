package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Dormitory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    LocalDate dob;
    String area;
    Address address;
    ContactInfo contactInfo;
    String description;

    @OneToMany(mappedBy = "dormitory")
    Set<Student> students = new HashSet<>();

    @OneToOne
    SpiritualGuide spiritualGuide;

}
