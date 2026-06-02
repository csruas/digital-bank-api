package io.github.csruas.bank.digital_bank_api.account.controller;

import io.github.csruas.bank.digital_bank_api.account.dto.TransferRequest;
import io.github.csruas.bank.digital_bank_api.account.dto.TransferResponse;
import io.github.csruas.bank.digital_bank_api.account.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transfers", description = "Money transfer endpoints")
@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Transfer money between accounts")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }
}
