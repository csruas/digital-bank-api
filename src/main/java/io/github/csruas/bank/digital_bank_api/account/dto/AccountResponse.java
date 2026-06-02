package io.github.csruas.bank.digital_bank_api.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String customerName,
        String documentNumber,
        BigDecimal balance,
        LocalDateTime createdAt
) {
}