package bank_application.service;

import bank_application.model.AccountEntity;
import bank_application.model.BalanceResponse;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationsRepository operationsRepository;
    @Autowired
    private AccountService accountService;

    @Test
    void operation() {
        //Тест с ID который имеется в базе.
        AccountEntity accountEntity = new AccountEntity();
        BalanceResponse balanceResponse = new BalanceResponse();
        int quantityBefore = operationsRepository.findAll().size();
        AccountEntity accountEntityInBase = accountRepository.findById(1000L).get();
        accountEntity.setId(1000L);
        accountEntity.setBalance(BigDecimal.valueOf(1000));
        //Специально не указываем тип валюты для возникновения Exception.

        Throwable thrown = assertThrows(Exception.class, () -> {
            accountService.operation(accountEntity, BigDecimal.valueOf(41), 1, balanceResponse);
        });
        int quantityAfter = operationsRepository.findAll().size();
        AccountEntity accountEntityInBaseAfter = accountRepository.findById(1000L).get();
        Assertions.assertEquals(accountEntityInBase, accountEntityInBaseAfter);
        Assertions.assertEquals(quantityBefore, quantityAfter);

        //Тест с ID который не имеется в базе.
        AccountEntity accountEntity2 = new AccountEntity();
        BalanceResponse balanceResponse2 = new BalanceResponse();
        int quantityBefore2 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBase2 = accountRepository.findById(1200L).orElse(null);
        accountEntity2.setId(1200L);
        accountEntity2.setBalance(BigDecimal.valueOf(5000));
        accountEntity2.setTypeCurrency("usd");

        Throwable thrown2 = assertThrows(Exception.class, () -> {
            accountService.operation(accountEntity2, BigDecimal.valueOf(39), 2, balanceResponse2);
        });
        int quantityAfter2 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBaseAfter2 = accountRepository.findById(1200L).orElse(null);
        Assertions.assertEquals(accountEntityInBase2, accountEntityInBaseAfter2);
        Assertions.assertEquals(quantityBefore2, quantityAfter2);

        //Тест на успешную запись в базу.
        BalanceResponse balanceResponse3 = new BalanceResponse();
        int quantityBefore3 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBase3 = accountRepository.findById(1000L).get();
        accountService.operation(accountRepository.findById(1000L).get(), BigDecimal.valueOf(37), 2, balanceResponse3);
        int quantityAfter3 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBaseAfter3 = accountRepository.findById(1000L).get();
        Assertions.assertNotEquals(accountEntityInBase3.getBalance(), accountEntityInBaseAfter3.getBalance());
        Assertions.assertEquals(quantityBefore3 + 1, quantityAfter3 );
    }
}