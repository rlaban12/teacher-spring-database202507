package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long> {

}
