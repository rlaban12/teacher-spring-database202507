package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
    extends JpaRepository<Department, Long> {

}
