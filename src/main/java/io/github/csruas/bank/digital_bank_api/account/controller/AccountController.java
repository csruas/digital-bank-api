package io.github.csruas.bank.digital_bank_api.account.controller;

import io.github.csruas.bank.digital_bank_api.account.dto.AccountResponse;
import io.github.csruas.bank.digital_bank_api.account.dto.CreateAccountRequest;
import io.github.csruas.bank.digital_bank_api.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Accounts", description = "Account management endpoints")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new account")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.create(request);
    }

    @Operation(summary = "List all accounts")
    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @Operation(summary = "Find account by ID")
    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id) {
        return accountService.findById(id);
    }
}
