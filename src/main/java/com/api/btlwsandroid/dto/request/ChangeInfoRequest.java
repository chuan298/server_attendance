package com.api.btlwsandroid.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangeInfoRequest implements Serializable {
    private static final long serialVersionUID = 152415155519L;
    Integer student_id;
    String password;
    String imgbase64;
}
