package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository
    extends JpaRepository<Department, Long> {

    // N + 1 문제 해결을 위한 fetch join 사용
    @Query("SELECT d FROM Department d JOIN FETCH d.employees")
    List<Department> findAllByFetch();

}
