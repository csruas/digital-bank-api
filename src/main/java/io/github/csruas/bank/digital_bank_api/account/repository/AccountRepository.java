package io.github.csruas.bank.digital_bank_api.account.repository;

import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(String documentNumber);

}