package com.bka.ssi.controller.accreditation.company.application.factories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartyFactory<T, R extends Party> {

    default public R create(T input) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public R create(T input, String userName) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public List<R> create(MultipartFile input) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public List<R> create(MultipartFile input, String userName) throws Exception {
        throw new UnsupportedOperationException();
    }
}
