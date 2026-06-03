package io.github.csruas.bank.digital_bank_api.account.service;

import io.github.csruas.bank.digital_bank_api.account.dto.TransferRequest;
import io.github.csruas.bank.digital_bank_api.account.dto.TransferResponse;
import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import io.github.csruas.bank.digital_bank_api.account.entity.Transaction;
import io.github.csruas.bank.digital_bank_api.account.repository.AccountRepository;
import io.github.csruas.bank.digital_bank_api.account.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldTransferSuccessfully() {

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                BigDecimal.valueOf(100)
        );

        Account sourceAccount = Account.builder()
                .id(1L)
                .customerName("Source")
                .documentNumber("111")
                .balance(BigDecimal.valueOf(1000))
                .build();

        Account targetAccount = Account.builder()
                .id(2L)
                .customerName("Target")
                .documentNumber("222")
                .balance(BigDecimal.valueOf(500))
                .build();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .sourceAccountId(1L)
                .targetAccountId(2L)
                .amount(BigDecimal.valueOf(100))
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();

        when(accountRepository.findWithLockingById(1L))
                .thenReturn(Optional.of(sourceAccount));

        when(accountRepository.findWithLockingById(2L))
                .thenReturn(Optional.of(targetAccount));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        TransferResponse response = transactionService.transfer(request);

        assertNotNull(response);
        assertEquals(1L, response.transactionId());

        assertEquals(
                BigDecimal.valueOf(900),
                sourceAccount.getBalance()
        );

        assertEquals(
                BigDecimal.valueOf(600),
                targetAccount.getBalance()
        );

        verify(accountRepository, times(2))
                .save(any(Account.class));

        verify(notificationService)
                .notifyTransferCompleted(
                        any(Account.class),
                        any(Account.class),
                        any(Transaction.class)
                );
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                BigDecimal.valueOf(2000)
        );

        Account sourceAccount = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .build();

        Account targetAccount = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(100))
                .build();

        when(accountRepository.findWithLockingById(1L))
                .thenReturn(Optional.of(sourceAccount));

        when(accountRepository.findWithLockingById(2L))
                .thenReturn(Optional.of(targetAccount));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transfer(request)
        );

        assertEquals(
                "Insufficient balance.",
                exception.getMessage()
        );

        verify(transactionRepository, never())
                .save(any(Transaction.class));
    }
}



