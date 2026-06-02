package io.github.csruas.bank.digital_bank_api.account.controller;

import io.github.csruas.bank.digital_bank_api.account.dto.AccountResponse;
import io.github.csruas.bank.digital_bank_api.account.dto.CreateAccountRequest;
import io.github.csruas.bank.digital_bank_api.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.create(request);
    }
}
