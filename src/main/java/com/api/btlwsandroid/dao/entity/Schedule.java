package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`schedule`")
@Data
public class Schedule implements Serializable{
    private static final long serialVersionUID =  43121535354L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_subject_group")
    private SubjectGroup subjectGroup;

    @ManyToOne
    @JoinColumn(name="id_practice_group")
    private PracticeGroup practiceGroup;
}
