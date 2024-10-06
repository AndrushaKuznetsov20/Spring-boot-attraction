package com.trueman.attractions.specification;

import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationAttraction {
    public static Specification<Attraction> findByTypeAttraction(TypeAttraction typeAttraction) {
        return (root, query, cb) ->
                cb.equal(root.get("typeAttraction"), typeAttraction);
    }

    public static Specification<Attraction> findByLocality(Locality locality) {
        return (root, query, cb) ->
                locality != null ? cb.equal(root.get("locality"), locality) : cb.conjunction();
    }
}
