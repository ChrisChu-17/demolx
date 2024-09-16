package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstName;
    String lastName;
    LocalDate dob;

    @Embedded
    ContactInfo contactInfo;

    String hostPhone;

    @ManyToMany
    Set<Major> majors;

    int startYear;
    int endYear;

    @Embedded
    Address address;

    @OneToOne
    Transcript transcript;

    @ManyToMany
    Set<School> schools;

    @ManyToOne
    Dormitory dormitory;

    @ManyToMany
    Set<Role> roles;
}
