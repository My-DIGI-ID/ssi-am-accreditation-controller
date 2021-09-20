package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.EmployeeMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.EmployeeMongoDbRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Repository("employeeMongoDbFacade")
/* TODO - Consider implicit mapping for reflected MongoDB Documents, then facade is not needed */
public class EmployeeMongoDbFacade implements EmployeeRepository {

    private final EmployeeMongoDbRepository employeeMongoDbRepository;
    private final EmployeeMongoDbMapper employeeMongoDbMapper;

    public EmployeeMongoDbFacade(EmployeeMongoDbRepository employeeMongoDbRepository,
        EmployeeMongoDbMapper employeeMongoDbMapper) {
        this.employeeMongoDbRepository = employeeMongoDbRepository;
        this.employeeMongoDbMapper = employeeMongoDbMapper;
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeMongoDbDocument employeeMongoDbDocument =
            this.employeeMongoDbMapper.employeeToEmployeeMongoDbDocument(employee);
        EmployeeMongoDbDocument savedEmployeeMongoDBDocument =
            this.employeeMongoDbRepository.save(employeeMongoDbDocument);

        Employee savedEmployee =
            this.employeeMongoDbMapper
                .employeeMongoDbDocumentToEmployee(savedEmployeeMongoDBDocument);

        return savedEmployee;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> iterable) {
        List<EmployeeMongoDbDocument> employeeMongoDbDocuments = new ArrayList<>();
        iterable.forEach(employee -> employeeMongoDbDocuments
            .add(this.employeeMongoDbMapper
                .employeeToEmployeeMongoDbDocument(employee)));

        Iterable<EmployeeMongoDbDocument> savedEmployeeMongoDbDocuments =
            this.employeeMongoDbRepository.saveAll(employeeMongoDbDocuments);

        /* ToDo - List<S> looks weird, should actually be List<Employee> */
        List<S> savedEmployees = new ArrayList<>();
        savedEmployeeMongoDbDocuments
            .forEach(
                savedEmployeeMongoDbDocument -> savedEmployees.add((S) this.employeeMongoDbMapper
                    .employeeMongoDbDocumentToEmployee(savedEmployeeMongoDbDocument)));

        return savedEmployees;
    }

    @Override
    public Optional<Employee> findById(String id) {
        Optional<EmployeeMongoDbDocument> employeeMongoDbDocument =
            this.employeeMongoDbRepository.findById(id);

        Employee employee =
            this.employeeMongoDbMapper
                .employeeMongoDbDocumentToEmployee(employeeMongoDbDocument.get());

        return Optional.of(employee);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException(
            "Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<Employee> findAll() {
        Iterable<EmployeeMongoDbDocument> employeeMongoDbDocuments =
            this.employeeMongoDbRepository.findAll();
        List<Employee> employees = new ArrayList<>();

        employeeMongoDbDocuments.forEach(employeeMongoDbDocument -> employees
            .add(this.employeeMongoDbMapper
                .employeeMongoDbDocumentToEmployee(employeeMongoDbDocument)));

        return employees;

    }

    @Override
    public Iterable<Employee> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException(
            "Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException(
            "Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        this.employeeMongoDbRepository.deleteById(id);
    }

    @Override
    public void delete(Employee employee) {
        throw new UnsupportedOperationException(
            "Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Employee> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }
}
