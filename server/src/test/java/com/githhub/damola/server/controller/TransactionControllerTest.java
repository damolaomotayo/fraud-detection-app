package com.githhub.damola.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.githhub.damola.server.dto.CreateTransaction;
import com.githhub.damola.server.dto.TransactionDto;
import com.githhub.damola.server.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void getAllTransactions_shouldReturnListOfTransactions() throws Exception {
        List<TransactionDto> transactions = List.of(
                TransactionDto.builder()
                        .id(1L)
                        .user("Alice")
                        .amount(1000.0)
                        .timestamp(LocalDateTime.now())
                        .fraudulent(false)
                        .build(),
                TransactionDto.builder()
                        .id(2L)
                        .user("Bob")
                        .amount(500.0)
                        .timestamp(LocalDateTime.now())
                        .fraudulent(true)
                        .build());

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].user", is("Alice")))
                .andExpect(jsonPath("$[0].amount", is(1000.0)))
                .andExpect(jsonPath("$[0].fraudulent", is(false)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].user", is("Bob")))
                .andExpect(jsonPath("$[1].amount", is(500.0)))
                .andExpect(jsonPath("$[1].fraudulent", is(true)));

    }

    @Test
    void createTransaction_shouldReturnCreatedTransaction() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user("Charlie")
                .amount(750.0)
                .fraudulent(false)
                .build();

        TransactionDto response = TransactionDto.builder()
                .id(3L)
                .user("Charlie")
                .amount(750.0)
                .timestamp(LocalDateTime.now())
                .fraudulent(false)
                .build();

        when(transactionService.saveTransaction(any(CreateTransaction.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.user", is("Charlie")))
                .andExpect(jsonPath("$.amount", is(750.0)))
                .andExpect(jsonPath("$.fraudulent", is(false)))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenUserIsBlank() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user("")  // Blank user
                .amount(100.0)
                .fraudulent(false)
                .build();

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.user", is("user is required")));
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenUserIsNull() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user(null)  // Null user
                .amount(100.0)
                .fraudulent(false)
                .build();

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.user", is("user is required")));
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenAmountIsNull() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user("Alice")
                .amount(null)  // Null amount
                .fraudulent(false)
                .build();

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.amount", is("amount is required")));
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenFraudulentIsNull() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user("Alice")
                .amount(100.0)
                .fraudulent(null)  // Null fraudulent
                .build();

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.fraudulent", is("fraudulent is required")));
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenAllFieldsAreInvalid() throws Exception {
        CreateTransaction request = CreateTransaction.builder()
                .user(null)
                .amount(null)
                .fraudulent(null)
                .build();

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.user", is("user is required")))
                .andExpect(jsonPath("$.errors.amount", is("amount is required")))
                .andExpect(jsonPath("$.errors.fraudulent", is("fraudulent is required")));
    }

    @Test
    void getAllTransactions_ShouldReturnEmptyList_WhenNoTransactionsExist() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(List.of());

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}