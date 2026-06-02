package io.github.csruas.bank.digital_bank_api.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        Long transactionId,
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt
) {
}
