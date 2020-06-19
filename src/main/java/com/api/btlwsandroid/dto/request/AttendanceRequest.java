package com.api.btlwsandroid.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Valid
@Builder
@AllArgsConstructor
public class AttendanceRequest implements Serializable {
    private static final long serialVersionUID = 15241515129365519L;

    private String imgbase64;

    private Double longitude;

    private Double latitude;
}
