package com.api.btlwsandroid.dao.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`room`")
@Data
public class Room implements Serializable {
    private static final long serialVersionUID =  431535354L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;
}
