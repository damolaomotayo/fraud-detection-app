package com.githhub.damola.server.service;

import com.githhub.damola.server.dto.CreateTransaction;
import com.githhub.damola.server.dto.TransactionDto;
import com.githhub.damola.server.entity.Transaction;
import com.githhub.damola.server.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(this::mapToTransactionDto)
                .toList();
    }

    public TransactionDto saveTransaction(CreateTransaction transaction) {
        Transaction newTransaction = Transaction.builder()
                .user(transaction.getUser())
                .amount(transaction.getAmount())
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        transactionRepository.saveAndFlush(newTransaction);

        return mapToTransactionDto(newTransaction);
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .user(transaction.getUser())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .fraudulent(transaction.getFraudulent())
                .build();
    }
}
