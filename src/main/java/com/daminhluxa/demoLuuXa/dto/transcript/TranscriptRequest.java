package com.daminhluxa.demoLuuXa.dto.transcript;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranscriptRequest {

    @DecimalMin(value = "0.0", message = "INVALID_DOB")
    @DecimalMax(value = "4.0", message = "INVALID_TRANSCRIPT_WITH_MAX")
    String gpa;

    @NotBlank(message = "INVALID_TRANSCRIPT_GRADE")
    String grade;

    @NotBlank(message = "INVALID_TRANSCRIPT_SEMESTER")
    String semester;

    @NotBlank(message = "INVALID_TRANSCRIPT_SCHOOL_YEAR")
    String schoolYear;
}
