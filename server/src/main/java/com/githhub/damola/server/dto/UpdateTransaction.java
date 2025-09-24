package com.githhub.damola.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTransaction {
    private Double amount;
    private Boolean fraudulent;
}
