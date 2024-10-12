package com.daminhluxa.demoLuuXa.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.function.BiFunction;

@AllArgsConstructor
@Slf4j
public class GenericSpecificationImpl<T> implements GenericSpecification<T> {
    private final SearchCriteria criteria;
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getKey().contains(".")) {
            return handleJoinPredicate(root, criteriaBuilder, criteria);
        } else {
            return handleSimplePredicate(root, criteriaBuilder, criteria);
        }
    }

    private Predicate handleSimplePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
        String value = criteria.getValue().toString().trim();
        switch (criteria.getOperation()) {
            case ":":
                return handleUUIDComparison(root.get(criteria.getKey()), criteriaBuilder, criteria, criteriaBuilder::equal);
            case "!:": // not equal
                return handleUUIDComparison(root.get(criteria.getKey()), criteriaBuilder, criteria, criteriaBuilder::notEqual);
            case ">":
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), value);
            case "<":
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), value);
            case "~":
                return criteriaBuilder.like(criteriaBuilder.lower(
                        root.get(criteria.getKey())), "%" + value.toLowerCase() + "%");
            case "IN":
                return root.get(criteria.getKey()).in(criteria.getValue());
            default:
                throw new IllegalArgumentException("Operation not supported: " + criteria.getOperation());
        }
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private Predicate handleUUIDComparison(Expression<?> expression, CriteriaBuilder criteriaBuilder, SearchCriteria criteria,
                                           BiFunction<Expression<?>, Object, Predicate> predicateFunction) {
        if (criteria.getValue() instanceof String && isValidUUID(criteria.getValue().toString())) {
            return predicateFunction.apply(expression, UUID.fromString(criteria.getValue()).toString());
        } else {
            return predicateFunction.apply(expression, criteria.getValue());
        }
    }

    private Predicate handleJoinPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
        String[] keysParts = criteria.getKey().split("\\.");
        String entityKey = keysParts[0];
        String fieldKey = keysParts[1];
        log.info("split: " + entityKey + "." + fieldKey);

        Join<Object, T> join = root.join(entityKey);
        switch (criteria.getOperation()) {
            case ":":
                return handleUUIDComparison(join.get(fieldKey), criteriaBuilder, criteria, criteriaBuilder::equal);
            case "!:": // not equal
                return handleUUIDComparison(join.get(fieldKey), criteriaBuilder, criteria, criteriaBuilder::notEqual);
            case ">":
                return criteriaBuilder.greaterThan(join.get(fieldKey), criteria.getValue().toString());
            case "<":
                return criteriaBuilder.lessThan(join.get(fieldKey), criteria.getValue().toString());
            case "LIKE":
                return criteriaBuilder.like(join.get(fieldKey), "%" + criteria.getValue() + "%");
            case "IN":
                return join.get(fieldKey).in(criteria.getValue());
            default:
                throw new IllegalArgumentException("Operation not supported: " + criteria.getOperation());
        }
    }
}
