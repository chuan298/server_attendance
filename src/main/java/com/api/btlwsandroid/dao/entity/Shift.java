package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`shift`")
@Data
public class Shift implements Serializable {

    private static final long serialVersionUID =  434143L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="start_lesson")
    private Integer startLesson;

    @Column(name="end_lesson")
    private Integer endLesson;
}
