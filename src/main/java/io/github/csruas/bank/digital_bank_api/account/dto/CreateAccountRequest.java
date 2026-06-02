package io.github.csruas.bank.digital_bank_api.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank String customerName,
        @NotBlank String documentNumber,
        @NotNull @DecimalMin("0.00") BigDecimal initialBalance
) {
}