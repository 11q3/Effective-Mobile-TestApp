package com.elevenqtwo.Effective_Mobile_TestApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @JsonProperty("source_user_id")
    private Long sourceUserId;

    @JsonProperty("destination_user_id")
    private Long destinationUserId;

    private BigDecimal amount;
}
