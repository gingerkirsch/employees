package com.employees.app.service;

import com.employees.app.model.Employee;
import com.employees.app.repository.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeJpaRepository repo;

    public List<Employee> listAll() {
        return repo.findAll();
    }

    public void save(Employee employee){
        repo.save(employee);
    }

    public Employee get(Long id){
        return repo.findById(id).get();
    }

    public Employee getByUsername(String username) {
        Employee employee = new Employee();
        employee.setUsername(username);
        ExampleMatcher usernameMatcher = ExampleMatcher.matchingAny()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Employee> example = Example.of(employee, usernameMatcher);
        Optional<Employee> employeeMaybe = repo.findOne(example);
        if (employeeMaybe.isPresent()){
            return employeeMaybe.get();
        }
        else{
            throw new EntityNotFoundException(username);
        }
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
