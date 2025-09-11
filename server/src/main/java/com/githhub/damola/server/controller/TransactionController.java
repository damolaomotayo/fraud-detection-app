package com.githhub.damola.server.controller;

import com.githhub.damola.server.dto.CreateTransaction;
import com.githhub.damola.server.dto.TransactionDto;
import com.githhub.damola.server.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody @Valid CreateTransaction request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.saveTransaction(request));
    }
}
