package com.bka.ssi.controller.accreditation.company.application.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseRepository<T, ID> {

    <S extends T> S save(S entity) throws Exception;

    <S extends T> Iterable<S> saveAll(Iterable<S> entities) throws Exception;

    Optional<T> findById(ID id) throws Exception;

    boolean existsById(ID id) throws Exception;

    Iterable<T> findAll() throws Exception;

    Iterable<T> findAllById(Iterable<ID> ids) throws Exception;

    long count() throws Exception;

    void deleteById(ID id) throws Exception;

    void delete(T entity) throws Exception;

    void deleteAllById(Iterable<? extends ID> ids) throws Exception;

    void deleteAll(Iterable<? extends T> entities) throws Exception;

    void deleteAll() throws Exception;
}
