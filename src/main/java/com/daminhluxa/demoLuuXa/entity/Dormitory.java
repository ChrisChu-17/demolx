package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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

    String name;
    LocalDate dob;
    String area;
    Address address;
    ContactInfo contactInfo;
    String description;

    @OneToOne
    SpiritualGuide spiritualGuide;

}
