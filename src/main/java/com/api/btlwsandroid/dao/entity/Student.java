package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="`student`")
@Data
public class Student implements Serializable {

    private static final long serialVersionUID =  53263242L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="birthday")
    private String birthday;

    @Column(name="phone_number")
    private String phone;

    @Column(name = "avatar")
    private String avatar;
}
