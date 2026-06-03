package io.github.csruas.bank.digital_bank_api.account.service;

import io.github.csruas.bank.digital_bank_api.account.dto.AccountResponse;
import io.github.csruas.bank.digital_bank_api.account.dto.CreateAccountRequest;
import io.github.csruas.bank.digital_bank_api.account.entity.Account;
import io.github.csruas.bank.digital_bank_api.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        CreateAccountRequest request = new CreateAccountRequest(
                "Cristiano Renuncia",
                "12345678900",
                BigDecimal.valueOf(1000)
        );

        Account savedAccount = Account.builder()
                .id(1L)
                .customerName(request.customerName())
                .documentNumber(request.documentNumber())
                .balance(request.initialBalance())
                .createdAt(LocalDateTime.now())
                .build();

        when(accountRepository.findByDocumentNumber(request.documentNumber()))
                .thenReturn(Optional.empty());

        when(accountRepository.save(any(Account.class)))
                .thenReturn(savedAccount);

        AccountResponse response = accountService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Cristiano Renuncia", response.customerName());
        assertEquals("12345678900", response.documentNumber());
        assertEquals(BigDecimal.valueOf(1000), response.balance());

        verify(accountRepository).findByDocumentNumber(request.documentNumber());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenDocumentNumberAlreadyExists() {
        CreateAccountRequest request = new CreateAccountRequest(
                "Cristiano Renuncia",
                "12345678900",
                BigDecimal.valueOf(1000)
        );

        Account existingAccount = Account.builder()
                .id(1L)
                .customerName("Existing Customer")
                .documentNumber(request.documentNumber())
                .balance(BigDecimal.valueOf(500))
                .createdAt(LocalDateTime.now())
                .build();

        when(accountRepository.findByDocumentNumber(request.documentNumber()))
                .thenReturn(Optional.of(existingAccount));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.create(request)
        );

        assertEquals("Document number already registered.", exception.getMessage());

        verify(accountRepository).findByDocumentNumber(request.documentNumber());
        verify(accountRepository, never()).save(any(Account.class));
    }
}
