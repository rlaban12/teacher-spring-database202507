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


    @Test
    @DisplayName("특정 부서를 조회하면 해당 소속 사원들이 함께 조회된다.")
    void findDeptTest() {
        //given
        Long deptId = 1L;
        //when
        Department foundDept = departmentRepository.findById(deptId).orElseThrow();
        //then
        System.out.println("foundDept = " + foundDept);
        System.out.println(foundDept.getEmployees());
    }


    @Test
    @DisplayName("양방향 매핑에서 데이터를 수정할 때 발생하는 문제")
    void changeTest() {
        //given

        // 3번 사원의 부서를 2번부서에서 1번 부서로 수정

        // 3번 사원 조회
        Employee foundEmp = employeeRepository.findById(3L).orElseThrow();
//        System.out.println("foundEmp = " + foundEmp + foundEmp.getDepartment());

        // 1번 부서 조회
        Department foundDept = departmentRepository.findById(1L).orElseThrow();

        //when
//        foundEmp.setDepartment(foundDept); // 사원쪽에서 부서정보 변경

        // 양방향에서는 반대편에서도 수동으로 변경처리가 진행되어야 함.
//        foundDept.getEmployees().add(foundEmp);

        // 연관관계 양방향 수정 편의메서드
        foundEmp.changeDepartment(foundDept);

        employeeRepository.save(foundEmp);

        //then
        System.out.println("\n\n변경 후 사원정보 = " + foundEmp + foundEmp.getDepartment());

        // 1번 부서의 사원정보를 다시 조회 -> 예상: ?? 3명
        List<Employee> employees = foundDept.getEmployees();
        System.out.println("\n\nemployees = " + employees);
        System.out.println("employees.size = " + employees.size());

    }


    @Test
    @DisplayName("부서가 제거되면 CASCADE REMOVE에 의해 해당 부서 사원이 모두 삭제된다")
    void cascadeTest() {
        //given
        Long deptId = 1L;
        //when
        departmentRepository.deleteById(deptId);
        //then
    }


    @Test
    @DisplayName("양방향 매핑 리스트에서 사원을 추가하면 DB에도 INSERT된다")
    void persistTest() {
        //given
        // 2번 부서 조회
        Department department = departmentRepository.findById(2L).orElseThrow();
        // 새로운 사원 생성
        Employee employee = Employee.builder()
                .name("파이리")
//                .department(department)
                .build();
        //when
//        employeeRepository.save(employee);
//        department.getEmployees().add(employee);

        department.addEmployee(employee);

        em.flush();
        em.clear();

        //then
        Employee foundEmp = employeeRepository.findById(5L).orElseThrow();
        System.out.println("foundEmp = " + foundEmp);
        System.out.println("foundEmp.getDepartment() = " + foundEmp.getDepartment());
    }


    @Test
    @DisplayName("양방향 매핑 리스트에서 사원을 제거하면 실제 DB에서 DELETE된다")
    void orphanRemovalTest() {
        //given
        // 1번 부서 조회
        Department foundDept = departmentRepository.findById(1L).orElseThrow();

        // 1번 부서의 모든 사원 조회
        List<Employee> employees = foundDept.getEmployees();

        // 1번 사원을 지우고 싶음
        employees.remove(0);

        //when

        //then
    }


    @Test
    @DisplayName("N + 1문제 확인")
    void nPlusOneTest() {
        //given

        //when
        List<Department> departments = departmentRepository.findAll();
        //then
        departments.forEach(dept -> {
            System.out.println("dept + dept.getEmployees() = "
                    + dept + dept.getEmployees());
        });
    }


    @Test
    @DisplayName("N + 1 문제 해결을 위한 fetch join")
    void fetchJoinTest() {
        //given

        //when
        List<Department> departments = departmentRepository.findAllByFetch();
        //then
        departments.forEach(d -> {
            System.out.println("d + d.getEmployees() = " + d + d.getEmployees());
        });
    }


}