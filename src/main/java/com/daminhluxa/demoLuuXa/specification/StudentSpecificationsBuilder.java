package com.daminhluxa.demoLuuXa.specification;

import com.daminhluxa.demoLuuXa.entity.Student;
import com.daminhluxa.demoLuuXa.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class StudentSpecificationsBuilder {
    List<SearchCriteria> params;

    public StudentSpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public StudentSpecificationsBuilder with(String key, String operator, String value) {
        log.info("with key = {}, operator = {}, value = {}", key, operator, value);
        params.add(new SearchCriteria(key, operator, value));
        return this;
    }

    public Specification<Student> build() {
        if(params.size() == 0) {
            return null;
        }

        List<Specification<Student>> specs = new ArrayList<>();
        for(SearchCriteria criteria : params) {
            specs.add(new StudentSpecification(criteria));
        }

        Specification<Student> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
