package io.github.csruas.bank.digital_bank_api.account.service;

import io.github.csruas.bank.digital_bank_api.account.dto.AccountResponse;
import io.github.csruas.bank.digital_bank_api.account.dto.CreateAccountRequest;
import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import io.github.csruas.bank.digital_bank_api.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse create(CreateAccountRequest request) {
        accountRepository.findByDocumentNumber(request.documentNumber())
                .ifPresent(account -> {
                    throw new IllegalArgumentException("Document number already registered.");
                });

        Account account = Account.builder()
                .customerName(request.customerName())
                .documentNumber(request.documentNumber())
                .balance(request.initialBalance())
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        return toResponse(savedAccount);
    }


    public List<AccountResponse> findAll() {

        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AccountResponse findById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found."));

        return toResponse(account);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getCustomerName(),
                account.getDocumentNumber(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}