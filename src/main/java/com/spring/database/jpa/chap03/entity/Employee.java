package com.spring.database.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
// 연관관계 필드는 순환참조 방지를 위해 제외해야 함
@ToString(exclude = {"department"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 사원 N
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name; // 사원명

    // DBMS처럼 한쪽(N쪽)에 상대의 데이터를 포함시키는 전략
    // -> 단방향 매핑
    // ManyToOne은 무조건 LAZY를 걸어라
    @ManyToOne(fetch = FetchType.LAZY) // 필요없을 때는 조인을 하지 않는 전략
    @JoinColumn(name = "dept_id") // FK를 포함시키는건 DB패러다임에 맞춰야함
    private Department department; // 부서정보 통째로 포함


    // 부서 변경 편의 메서드
    public void changeDepartment(Department department) {
        // ManyToOne필드가 변경이 일어나면 반대편쪽의 OneToMany도 같이 갱신
        this.department = department;
        if (department != null) {
            department.getEmployees().add(this);
        }
    }
}
