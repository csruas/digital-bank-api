package io.github.csruas.bank.digital_bank_api.account.service;

import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import io.github.csruas.bank.digital_bank_api.account.entity.Transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void notifyTransferCompleted(
            Account sourceAccount,
            Account targetAccount,
            Transaction transaction
    ) {
        log.info(
                "Transfer notification sent. transactionId={}, sourceAccountId={}, targetAccountId={}, amount={}, status={}",
                transaction.getId(),
                sourceAccount.getId(),
                targetAccount.getId(),
                transaction.getAmount(),
                transaction.getStatus()
        );
    }
}
