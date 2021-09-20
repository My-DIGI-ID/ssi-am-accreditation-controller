package com.bka.ssi.controller.accreditation.company.application.factories;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Factory<T, R> {

    default public R create(T input) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public List<R> create(MultipartFile input) throws Exception {
        throw new UnsupportedOperationException();
    }
}
