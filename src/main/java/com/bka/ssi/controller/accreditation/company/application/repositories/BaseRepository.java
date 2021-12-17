/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.application.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Base repository.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@Repository
public interface BaseRepository<T, ID> {

    /**
     * Save s.
     *
     * @param <S>    the type parameter
     * @param entity the entity
     * @return the s
     * @throws Exception the exception
     */
    <S extends T> S save(S entity) throws Exception;

    /**
     * Save all iterable.
     *
     * @param <S>      the type parameter
     * @param entities the entities
     * @return the iterable
     * @throws Exception the exception
     */
    <S extends T> Iterable<S> saveAll(Iterable<S> entities) throws Exception;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws Exception the exception
     */
    Optional<T> findById(ID id) throws Exception;

    /**
     * Exists by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws Exception the exception
     */
    boolean existsById(ID id) throws Exception;

    /**
     * Find all iterable.
     *
     * @return the iterable
     * @throws Exception the exception
     */
    Iterable<T> findAll() throws Exception;

    /**
     * Find all by id iterable.
     *
     * @param ids the ids
     * @return the iterable
     * @throws Exception the exception
     */
    Iterable<T> findAllById(Iterable<ID> ids) throws Exception;

    /**
     * Count long.
     *
     * @return the long
     * @throws Exception the exception
     */
    long count() throws Exception;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws Exception the exception
     */
    void deleteById(ID id) throws Exception;

    /**
     * Delete.
     *
     * @param entity the entity
     * @throws Exception the exception
     */
    void delete(T entity) throws Exception;

    /**
     * Delete all by id.
     *
     * @param ids the ids
     * @throws Exception the exception
     */
    void deleteAllById(Iterable<? extends ID> ids) throws Exception;

    /**
     * Delete all.
     *
     * @param entities the entities
     * @throws Exception the exception
     */
    void deleteAll(Iterable<? extends T> entities) throws Exception;

    /**
     * Delete all.
     *
     * @throws Exception the exception
     */
    void deleteAll() throws Exception;
}
