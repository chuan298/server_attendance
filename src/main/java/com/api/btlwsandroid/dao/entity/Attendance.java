package com.api.btlwsandroid.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="`attendance`")
public class Attendance implements Serializable {
    private static final long serialVersionUID =  43165535354L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_student_course")
    private StudentCourse studentCourse;

    @Column(name = "time")
    private String time;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;

    @Column(name = "type_schedule")
    private Integer type_schedule;
}
