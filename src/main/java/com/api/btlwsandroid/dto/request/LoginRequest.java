package com.api.btlwsandroid.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Valid
@Builder
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1524151511129365519L;

    @ApiModelProperty(example = "1", required = true)
    @NotEmpty(message = "validate.isRequired")
    @Size(max = 18, message = "validate.lengthMax;{max}")
    private String username;

    @ApiModelProperty(example = "1", required = true)
    @NotEmpty(message = "validate.isRequired")
    @Size(max = 18, message = "validate.lengthMax;{max}")
    private String password;

}
