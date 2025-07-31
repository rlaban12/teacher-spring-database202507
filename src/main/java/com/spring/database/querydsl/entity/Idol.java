package com.spring.database.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString(exclude = {"group"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_idol")
public class Idol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idol_id")
    private Long id;

    private String idolName;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Idol(String idolName, int age, Group group) {
        this.idolName = idolName;
        this.age = age;
        this.group = group;
    }

    // 아이돌의 그룹을 변경하는 메서드
    public void changeGroup(Group group) {
        this.group = group;
        group.getIdols().add(this);
    }

}
