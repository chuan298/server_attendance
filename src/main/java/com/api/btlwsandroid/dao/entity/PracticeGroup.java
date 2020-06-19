package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`practice_group`")
@Data
public class PracticeGroup implements Serializable {
    private static final long serialVersionUID =  43353554L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_subject_group")
    private SubjectGroup subjectGroup;

    @ManyToOne
    @JoinColumn(name="id_practice_shift")
    private Shift practiceShift;

    @ManyToOne
    @JoinColumn(name="id_practice_room")
    private Room practiceRoom;

    @Column(name="week_practice")
    private String weekPractice;

    @Column(name="group_practice")
    private Integer groupPractice;

    @Column(name="day_practice")
    private Integer dayPractice;
}
