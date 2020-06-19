package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="`student_course`")
@Data
public class StudentCourse implements Serializable {

    private static final long serialVersionUID =  532632423L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name="id_subject_group")
    private SubjectGroup subjectGroup;

    @Column(name="study_number")
    private Integer studyNumber;

}
