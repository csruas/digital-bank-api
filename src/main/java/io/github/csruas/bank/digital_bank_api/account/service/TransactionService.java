package io.github.csruas.bank.digital_bank_api.account.service;

import io.github.csruas.bank.digital_bank_api.account.dto.TransferRequest;
import io.github.csruas.bank.digital_bank_api.account.dto.TransferResponse;
import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import io.github.csruas.bank.digital_bank_api.account.entity.Transaction;
import io.github.csruas.bank.digital_bank_api.account.repository.AccountRepository;
import io.github.csruas.bank.digital_bank_api.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        if (request.sourceAccountId().equals(request.targetAccountId())) {
            throw new IllegalArgumentException("Source and target accounts must be different.");
        }

        Account sourceAccount = accountRepository.findWithLockingById(request.sourceAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found."));

        Account targetAccount = accountRepository.findWithLockingById(request.targetAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Target account not found."));

        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.amount()));
        targetAccount.setBalance(targetAccount.getBalance().add(request.amount()));

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = Transaction.builder()
                .sourceAccountId(sourceAccount.getId())
                .targetAccountId(targetAccount.getId())
                .amount(request.amount())
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransferResponse(
                savedTransaction.getId(),
                savedTransaction.getSourceAccountId(),
                savedTransaction.getTargetAccountId(),
                savedTransaction.getAmount(),
                savedTransaction.getStatus(),
                savedTransaction.getCreatedAt()
        );
    }
}
