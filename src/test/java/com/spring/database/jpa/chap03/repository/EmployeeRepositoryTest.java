package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Department;
import com.spring.database.jpa.chap03.entity.Employee;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional   // 연관관계 사용시 필수
@Rollback(false)
class EmployeeRepositoryTest {

    @Autowired EmployeeRepository employeeRepository;
    @Autowired DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void insertBulk() {
        // 부서정보 만들기
        Department d1 = Department.builder().name("영업부").build();
        Department d2 = Department.builder().name("개발부").build();

        List<Department> departments = departmentRepository.saveAllAndFlush(
                List.of(d1, d2)
        );

        // 사원정보 만들기
        Employee e1 = Employee.builder()
                .name("라이언")
                .department(departments.get(0))
                .build();

        Employee e2 = Employee.builder()
                .name("어피치")
                .department(departments.get(0))
                .build();

        Employee e3 = Employee.builder()
                .name("프로도")
                .department(departments.get(1))
                .build();

        Employee e4 = Employee.builder()
                .name("네오")
                .department(departments.get(1))
                .build();

        employeeRepository.saveAllAndFlush(
                List.of(e1, e2, e3, e4)
        );

        /*
            JPA는 영속성 컨텍스트라는 대형 데이터 창고를 운영한다.
            메모리에 데이터가 존재하면 굳이 또 DB가서 조회해오지 않고
            컨텍스트 창고안에있는 데이터를 재활용함.

            아래의 코드는 데이터 창고 즉, 영속성 컨텍스트를 지워버리는 코드
         */
        em.flush();
        em.clear();
    }


    @Test
    @DisplayName("특정 사원의 정보를 조회하면 부서정보도 같이 조회된다")
    void findTest() {
        //given
        Long empId = 4L;
        //when
        Employee employee = employeeRepository.findById(empId).orElseThrow();
        //then
        System.out.println("employee = " + employee);
        // 명시적으로 부서정보를 참조 - LAZY로딩은 이 시점에 부서정보를 가져옵니다.
        Department department = employee.getDepartment();
        System.out.println("department = " + department);
    }

}