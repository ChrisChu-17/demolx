package com.daminhluxa.demoLuuXa.entity;

import com.daminhluxa.demoLuuXa.enums.SpiritRole;
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
public class SpiritualGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstName;
    String lastName;

    @Enumerated(EnumType.STRING)
    SpiritRole role;

    @Embedded
    ContactInfo contactInfo;

    @OneToOne
    Dormitory dormitory;
}
