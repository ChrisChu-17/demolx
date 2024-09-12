package com.daminhluxa.demoLuuXa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfo {
    String primaryEmail;
    String secondaryEmail;
    String primaryPhone;
    String secondaryPhone;

    @Override
    public String toString() {
        return String.format("ContactInfo[primaryEmail=%s, secondaryEmail=%s, primaryPhone=%s, secondaryPhone=%s]",
                primaryEmail, secondaryEmail, primaryPhone, secondaryPhone);
    }
}
