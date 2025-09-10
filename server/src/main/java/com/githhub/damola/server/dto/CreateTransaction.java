package com.githhub.damola.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransaction {
    @NotBlank(message = "user is required")
    private String user;

    @NotNull(message = "amount is required")
    private Double amount;

    @NotNull(message = "fraudulent is required")
    private Boolean fraudulent;
}
