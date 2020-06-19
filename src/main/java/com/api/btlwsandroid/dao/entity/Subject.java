package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`subject`")
@Data
public class Subject implements Serializable {

    private static final long serialVersionUID =  -42142141L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="credit_number")
    private Integer creditNumber;

    @Column(name="lesson_number_1")
    private Integer lessonNumber1;

    @Column(name="lesson_number_2")
    private Integer lessonNumber2;

}
