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
public class RecognizeResponse implements Serializable {
    private static final long serialVersionUID = 191515111265519L;
    String maSv;
    Double top_left_x, top_left_y, bottom_right_x, bottom_right_y;
}
