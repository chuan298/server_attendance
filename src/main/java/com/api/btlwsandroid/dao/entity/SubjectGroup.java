package com.api.btlwsandroid.dao.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "`subject_group`")
@Data
public class SubjectGroup implements Serializable {

    private static final long serialVersionUID =  3214214215L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @OneToOne
    @JoinColumn(name="id_subject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name="id_shift_1")
    private Shift shift1;

    @ManyToOne
    @JoinColumn(name="id_shift_2")
    private Shift shift2;

    @Column(name="day")
    private Integer day;

    @Column(name="week_1")
    private String week1;

    @Column(name="week_2")
    private String week2;

    @Column(name="group_number")
    private Integer groupNumber;

    @Column(name="code")
    private String code;

    @Column(name = "teacher")
    private String teacher;


}
