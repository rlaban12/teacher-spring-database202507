package com.spring.database.jpa.chap02.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
// @Setter // 객체의 불변성
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;   // PK를 string으로

    @Column(name = "stu_name", nullable = false)
    private String name; // 학생 이름

    private String city;

    private String major;

}
