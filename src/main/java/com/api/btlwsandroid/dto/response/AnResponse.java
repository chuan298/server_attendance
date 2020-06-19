package com.api.btlwsandroid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnResponse {
    @JsonProperty()
    String imgbase64;
}
