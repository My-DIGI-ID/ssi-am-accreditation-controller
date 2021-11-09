package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.EmployeeMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.EmployeeMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/* TODO - Consider implicit mapping for reflected MongoDB Documents, then facade is not needed */
public class EmployeeMongoDbFacade implements EmployeeRepository {

    private final EmployeeMongoDbRepository repository;
    private final EmployeeMongoDbMapper mapper;
    private final Logger logger;

    public EmployeeMongoDbFacade(EmployeeMongoDbRepository employeeMongoDbRepository,
        EmployeeMongoDbMapper employeeMongoDbMapper, Logger logger) {
        this.repository = employeeMongoDbRepository;
        this.mapper = employeeMongoDbMapper;
        this.logger = logger;
    }

    @Override
    public Employee save(Employee employee) {
        logger.debug("Saving employee");

        EmployeeMongoDbDocument document = this.mapper.entityToDocument(employee);
        EmployeeMongoDbDocument savedDocument = this.repository.save(document);

        Employee savedEmployee = this.mapper.documentToEntity(savedDocument);

        return savedEmployee;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> iterable) {
        logger.debug("Saving employees");

        List<EmployeeMongoDbDocument> documents = new ArrayList<>();
        iterable.forEach(employee -> documents.add(this.mapper.entityToDocument(employee)));

        Iterable<EmployeeMongoDbDocument> savedDocuments = this.repository.saveAll(documents);

        /* ToDo - List<S> looks weird, should actually be List<Employee> */
        List<S> savedEmployees = new ArrayList<>();
        savedDocuments.forEach(savedDocument -> savedEmployees.add((S) this.mapper
            .documentToEntity(savedDocument)));

        return savedEmployees;
    }

    @Override
    public Optional<Employee> findById(String id) {
        logger.debug("Fetching employee with id " + id);

        Optional<EmployeeMongoDbDocument> document = this.repository.findById(id);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        Employee employee = this.mapper.documentToEntity(document.get());

        return Optional.of(employee);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<Employee> findAll() {
        logger.debug("Fetching all employees");

        Iterable<EmployeeMongoDbDocument> documents = this.repository.findAll();

        List<Employee> employees = new ArrayList<>();
        documents.forEach(document -> employees.add(this.mapper.documentToEntity(document)));

        return employees;

    }

    @Override
    public Iterable<Employee> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        logger.debug("Deleting employee with id " + id);

        this.repository.deleteById(id);
    }

    @Override
    public void delete(Employee employee) {
        throw new UnsupportedOperationException("Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Employee> iterable) {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public Optional<Employee> findByIdAndCreatedBy(String id, String createdBy) {
        logger.debug("Fetching employee with id " + id + " and creator " + createdBy);

        Optional<EmployeeMongoDbDocument> document =
            this.repository.findByIdAndCreatedBy(id, createdBy);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        Employee employee = this.mapper.documentToEntity(document.get());

        return Optional.of(employee);
    }

    @Override
    public List<Employee> findAllByCreatedBy(String createdBy) {
        logger.debug("Fetching all employees by creator " + createdBy);

        List<EmployeeMongoDbDocument> documents = this.repository.findAllByCreatedBy(createdBy);

        List<Employee> employees = new ArrayList<>();
        documents.forEach(document -> employees.add(this.mapper.documentToEntity(document)));

        return employees;
    }
}
