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
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    String code;
    Address address;
}

