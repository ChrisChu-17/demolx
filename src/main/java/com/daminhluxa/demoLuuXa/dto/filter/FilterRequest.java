package com.daminhluxa.demoLuuXa.dto.filter;

import com.daminhluxa.demoLuuXa.specification.SearchCriteria;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterRequest {
//    List<SearchCriteria> filters;
    String filter;
    String sort;
}
