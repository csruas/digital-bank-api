package io.github.csruas.bank.digital_bank_api.account.repository;

import io.github.csruas.bank.digital_bank_api.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrTargetAccountIdOrderByCreatedAtDesc(
            Long sourceAccountId,
            Long targetAccountId
    );
}
