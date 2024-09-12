package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    String grade;
    String semester;
    String schoolYear;
    String rating;
}
