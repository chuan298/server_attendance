package com.api.btlwsandroid.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Valid
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse implements Serializable {
    private static final long serialVersionUID = 1915151112659L;

    private RecognizeResponse recognizeResponse;

    private Boolean isAttendant;

    private String message;
}
