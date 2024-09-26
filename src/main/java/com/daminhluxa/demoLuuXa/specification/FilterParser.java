package com.daminhluxa.demoLuuXa.specification;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class FilterParser {

    //filter = lastName~Doe
    public List<SearchCriteria> parseFilterString(String filter) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (filter == null || filter.trim().isEmpty()) {
            return criteriaList; // Return an empty list if there's no filter
        }

        // lastName~Doe
        String[] filters = filter.split("&");
        for (String filterItem : filters) {

            String operator = null;

            if(filterItem.contains("~")) {
                operator = "LIKE";
            } else if (filterItem.contains("=")) {
                operator = "EQUAL";
            } else if (filterItem.contains("!=")) {
                operator = "NOT_EQUAL";
            } else if (filterItem.contains(">")) {
                operator = "GREATER_THAN";
            } else if (filterItem.contains("<")) {
                operator = "LESS_THAN";
            }

            String[] parts = filterItem.split("(?<=[~!=><])|(?=[~!=><])");

            if(parts.length == 3) {
                String key = parts[0].trim();
                String value = parts[2].trim();

                SearchCriteria searchCriteria = new SearchCriteria(key, operator, value);
                criteriaList.add(searchCriteria);
            }
        }
        return criteriaList;
    }
}
