package com.daminhluxa.demoLuuXa.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranscriptCreationRequest {
    String grade;
    String semester;
    String rating;
}
