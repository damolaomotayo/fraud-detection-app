package com.githhub.damola.server.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private long id;
    private String user;
    private Double amount;
    private LocalDateTime timestamp;
    private Boolean fraudulent;
}
