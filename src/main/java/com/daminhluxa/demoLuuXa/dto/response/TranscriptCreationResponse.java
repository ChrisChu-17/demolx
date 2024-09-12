package com.daminhluxa.demoLuuXa.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranscriptCreationResponse {
    String grade;
    String semester;
    String schoolYear;
    String rating;
}
