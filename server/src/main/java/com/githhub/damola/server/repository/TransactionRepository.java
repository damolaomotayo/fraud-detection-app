package com.githhub.damola.server.repository;

import com.githhub.damola.server.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
