package com.daminhluxa.demoLuuXa.specification;

import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.Student;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;
import java.util.function.BiFunction;

@AllArgsConstructor
public class StudentSpecification implements Specification<Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentSpecification.class);
    private final SearchCriteria criteria;


//    @Override
//    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        switch (criteria.getOperation()) {
//            case ":":
//                log.info("Key: {}", criteria.getKey());
//                log.info("Operation: {}", criteria.getOperation());
//                log.info("Value: {}", criteria.getValue());
//                if(criteria.getKey().contains(".")) {
//                    log.info("vào join");
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    log.info("vào else");
//                    return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
//                }
//            case "!:":
//                if(criteria.getKey().contains(".")) {
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//                }
//            case ">":
//                if (criteria.getKey().contains(".")) {
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
//                }
//            case "<":
//                if (criteria.getKey().contains(".")) {
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
//                }
//            case "LIKE":
//                if (criteria.getKey().contains(".")) {
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//                }
//            case "IN":
//                if (criteria.getKey().contains(".")) {
//                    return handleJoinPredicate(root, criteriaBuilder, criteria);
//                } else {
//                    return root.get(criteria.getKey()).in(criteria.getValue());
//                }
//            default:
//                throw new IllegalArgumentException("Operation not supported: " + criteria.getOperation());
//        }
//    }

    private Predicate handleSimplePredicate(Root<Student> root,CriteriaBuilder criteriaBuilder,
                                            SearchCriteria criteria) {
        switch (criteria.getOperation()) {
            case ":":
                return handleUUIDComparison(root.get(criteria.getKey()), criteriaBuilder, criteria, criteriaBuilder::equal);
            case "!:":
                return handleUUIDComparison(root.get(criteria.getKey()), criteriaBuilder, criteria, criteriaBuilder::notEqual);
            case ">":
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case "<":
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case "LIKE":
                return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case "IN":
                return root.get(criteria.getKey()).in(criteria.getValue());
            default:
                throw new IllegalArgumentException("Operation not supported: " + criteria.getOperation());
        }
    }

    private Predicate handleUUIDComparison(Expression<?> expression, CriteriaBuilder criteriaBuilder, SearchCriteria criteria,
                                           BiFunction<Expression<?>, Object, Predicate> predicateFunction) {
        if (criteria.getValue() instanceof String && isValidUUID(criteria.getValue().toString())) {
            return predicateFunction.apply(expression,
                    UUID.fromString(criteria.getValue()).toString());
        } else {
            return predicateFunction.apply(expression, criteria.getValue());
        }
    }

    private Predicate handleJoinPredicate(Root<Student> root,
                                          CriteriaBuilder criteriaBuilder,
                                          SearchCriteria criteria) {
        String[] keysParts = criteria.getKey().split("\\.");
        String entityKey = keysParts[0];
        String fieldKey = keysParts[1];
        log.info("split: "+entityKey + "." + fieldKey);

        Join<Object, Student> join = root.join(entityKey);
        switch (criteria.getOperation()) {
            case ":":
                return handleUUIDComparison(join.get(fieldKey), criteriaBuilder, criteria, criteriaBuilder::equal);
            case "!:":
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

    private boolean isValidUUID(String uuid) {
        try {UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getKey().contains(".")) {
            log.info("vô join");
            return handleJoinPredicate(root, criteriaBuilder, criteria);
        } else {
            return handleSimplePredicate(root, criteriaBuilder, criteria);
        }
    }
}
