package com.githhub.damola.server.service;

import com.githhub.damola.server.dto.CreateTransaction;
import com.githhub.damola.server.dto.TransactionDto;
import com.githhub.damola.server.dto.UpdateTransaction;
import com.githhub.damola.server.entity.Transaction;
import com.githhub.damola.server.exception.BadRequestException;
import com.githhub.damola.server.exception.NotFoundException;
import com.githhub.damola.server.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    void saveTransaction() {
        CreateTransaction createTransaction = CreateTransaction.builder()
                .user("Alice")
                .amount(1000.0)
                .build();

        Transaction savedTransaction = Transaction.builder()
                .id(1L)
                .user("Alice")
                .amount(1000.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        when(transactionRepository.saveAndFlush(any(Transaction.class)))
                .thenReturn(savedTransaction);

        TransactionDto result = undertest.saveTransaction(createTransaction);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).saveAndFlush(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();
        assertThat(capturedTransaction.getUser()).isEqualTo("Alice");
        assertThat(capturedTransaction.getAmount()).isEqualTo(1000.0);
        assertThat(capturedTransaction.getFraudulent()).isFalse();
        assertThat(capturedTransaction.getTimestamp()).isNotNull();
        assertThat(capturedTransaction.getId()).isNull();

        // Verify the returned DTO
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUser()).isEqualTo("Alice");
        assertThat(result.getAmount()).isEqualTo(1000.0);
        assertThat(result.getFraudulent()).isFalse();
        assertThat(result.getTimestamp()).isEqualTo(savedTransaction.getTimestamp());
    }

    @Test
    void updateTransaction_success() {
        Transaction transaction = transactions.get(0);

        Transaction savedTransaction = Transaction.builder()
                .id(1L)
                .user("Alice")
                .amount(250.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        UpdateTransaction updateRequest = UpdateTransaction.builder()
                .amount(250.0)
                .build();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionDto updateResponse = undertest.updateTransaction(updateRequest, 1L);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();
        assertThat(capturedTransaction.getUser()).isEqualTo("Alice");
        assertThat(capturedTransaction.getAmount()).isEqualTo(250.0);

        assertThat(updateResponse.getAmount()).isEqualTo(updateRequest.getAmount());
        assertThat(updateResponse.getFraudulent()).isEqualTo(transaction.getFraudulent());

        assertThat(transaction).isNotNull();

        verify(transactionRepository).findById(anyLong());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void updateTransaction_invalidTransaction() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        UpdateTransaction updateRequest = UpdateTransaction.builder()
                .fraudulent(true)
                .build();

        assertThatThrownBy(() -> undertest.updateTransaction(updateRequest, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("transaction not found with id");

        verify(transactionRepository).findById(anyLong());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void updateTransaction_updateFieldNotProvided() {
        UpdateTransaction updateRequest = UpdateTransaction.builder().build();
        assertThatThrownBy(() -> undertest.updateTransaction(updateRequest, 1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Provide the field you want to update");

        verify(transactionRepository, never()).findById(anyLong());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_success() {
        Transaction transaction = transactions.get(0);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        undertest.deleteTransaction(1L);

        assertThat(transaction).isNotNull();

        verify(transactionRepository).findById(anyLong());
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void deleteTransaction_invalidTransaction() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> undertest.deleteTransaction(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("transaction not found");
    }

}