package com.employees.app.repository;

import com.employees.app.model.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>,
        QueryByExampleExecutor<Employee> {
}
