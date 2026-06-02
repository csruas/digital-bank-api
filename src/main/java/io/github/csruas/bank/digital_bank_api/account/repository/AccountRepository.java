package io.github.csruas.bank.digital_bank_api.account.repository;

import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(String documentNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockingById(Long id);

}