package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long> {

//    @Query(value = """
//        SELECT *
//        FROM tbl_emp
//        WHERE dept_id = ?1
//    """, nativeQuery = true)
//    List<Employee> findByDeptId(Long deptId);
}
