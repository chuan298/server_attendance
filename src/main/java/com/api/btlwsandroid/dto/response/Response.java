package com.api.btlwsandroid.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 2182083386459295890L;

    private String code;

    private String message;

    private T data;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

//    public Collection<Object> getData() {
//    }

//    public long getCode() {
//    }
//
//    public long getMessage() {
//    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
