package io.github.csruas.bank.digital_bank_api.account.repository;

import io.github.csruas.bank.digital_bank_api.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
