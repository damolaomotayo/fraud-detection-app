package com.githhub.damola.server.service;

import com.githhub.damola.server.dto.CreateTransaction;
import com.githhub.damola.server.dto.TransactionDto;
import com.githhub.damola.server.dto.UpdateTransaction;
import com.githhub.damola.server.entity.Transaction;
import com.githhub.damola.server.exception.BadRequestException;
import com.githhub.damola.server.exception.NotFoundException;
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

        Transaction savedTransaction = transactionRepository.saveAndFlush(newTransaction);

        return mapToTransactionDto(savedTransaction);
    }

    public TransactionDto updateTransaction(UpdateTransaction updateRequest, Long id) {
        Transaction transaction = getTransaction(id);

        if (updateRequest.getFraudulent() == null && updateRequest.getAmount() == null) {
            throw new BadRequestException("Provide the field you want to update");
        }

        if (updateRequest.getAmount() != null) {
            transaction.setAmount(updateRequest.getAmount());
        }

        if (updateRequest.getFraudulent() != null) {
            transaction.setFraudulent(updateRequest.getFraudulent());
        }

        transaction = transactionRepository.save(transaction);

        return mapToTransactionDto(transaction);
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = getTransaction(id);

        transactionRepository.delete(transaction);
    }

    private Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("transaction not found with id"));

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
