package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transcript {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String gpa;
    String grade;
    String semester;
    String schoolYear;
    String rating;

    @ManyToOne
    Student student;
}
