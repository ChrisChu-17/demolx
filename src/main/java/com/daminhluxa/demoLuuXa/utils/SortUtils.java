package com.daminhluxa.demoLuuXa.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortUtils {
    public static List<Sort.Order> parseSort(String sortParam) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sortParam != null && !sortParam.isEmpty()) {
            String[] sorts = sortParam.split(",");
            for (String sort : sorts) {
                String[] parts = sort.split(":");
                String property = parts[0];
                Sort.Direction direction = parts.length > 1 && parts[1].equals("desc")
                        ? Sort.Direction.DESC : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction, property));
            }
        }
        return orders;
    }
}