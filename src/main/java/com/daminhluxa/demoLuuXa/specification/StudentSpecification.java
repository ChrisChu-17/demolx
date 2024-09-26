package com.daminhluxa.demoLuuXa.specification;

import com.daminhluxa.demoLuuXa.entity.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class StudentSpecification implements Specification<Student> {

    private final SearchCriteria criteria;


    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperation()) {
            case "EQUAL":
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            case "NOT_EQUAL":
                return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case "GREATER_THAN":
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case "LESS_THAN":
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case "LIKE":
                return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case "IN":
                return root.get(criteria.getKey()).in(criteria.getValue());
            default:
                throw new IllegalArgumentException("Operation not supported: " + criteria.getOperation());
        }
    }
}
