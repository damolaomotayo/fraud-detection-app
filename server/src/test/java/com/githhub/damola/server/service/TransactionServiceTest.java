package com.githhub.damola.server.service;

import com.githhub.damola.server.entity.Transaction;
import com.githhub.damola.server.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService undertest;

    private List<Transaction> transactions;

    @BeforeEach
    void setup() {
        Transaction transaction1 = Transaction.builder()
                .id(12L)
                .user("Alice")
                .amount(2000.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(15L)
                .user("Dennis")
                .amount(220.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(true)
                .build();

        Transaction transaction3 = Transaction.builder()
                .id(21L)
                .user("Bob")
                .amount(10.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        transactions = new ArrayList<>();

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
    }

    @Test
    void getAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(transactions);

        var response = undertest.getAllTransactions();

        assertThat(response).hasSize(3);

        assertThat(response).extracting("id")
                .containsExactlyInAnyOrder(12L, 15L, 21L);

        assertThat(response).extracting("user")
                .containsExactlyInAnyOrder("Alice", "Dennis", "Bob");

        assertThat(response).extracting("amount")
                .containsExactlyInAnyOrder(2000.0, 220.0, 10.0);

        assertThat(response).extracting("fraudulent")
                .containsExactlyInAnyOrder(false, true, false);


        // Verify no null values in important fields
        assertThat(response).allSatisfy(transaction -> {
            assertThat(transaction.getUser()).isNotBlank();
            assertThat(transaction.getAmount()).isNotNull();
            assertThat(transaction.getTimestamp()).isNotNull();
        });
    }

    @Test
    @Disabled
    void saveTransaction() {
    }
}