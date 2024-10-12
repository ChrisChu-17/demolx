package com.daminhluxa.demoLuuXa.specification;

import com.daminhluxa.demoLuuXa.entity.Student;
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
public class SpecificationsBuilder<T> {
    List<SearchCriteria> params;

    public SpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public SpecificationsBuilder with(String key, String operator, String value) {
        params.add(new SearchCriteria(key, operator, value));
        return this;
    }

    public Specification<T> build() {
        if(params.size() == 0) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();
        for(SearchCriteria criteria : params) {
            specs.add(new GenericSpecificationImpl<>(criteria));
        }

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
