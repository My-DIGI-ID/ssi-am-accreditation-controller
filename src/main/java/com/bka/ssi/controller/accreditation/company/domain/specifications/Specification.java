package com.bka.ssi.controller.accreditation.company.domain.specifications;

public interface Specification<T> {

    Boolean isSatisfiedBy(T object);
}
