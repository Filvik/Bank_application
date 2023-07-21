package bank_application.service;

import bank_application.model.AccountEntity;
import bank_application.model.BalanceResponse;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransferServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationsRepository operationsRepository;
    @Autowired
    private TransferService transferService;

    @Test
    void operation() {

        //Тест на успешную запись в базу.
        AccountEntity accountEntitySender = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipient = accountRepository.findById(2000L).get();
        BalanceResponse balanceResponse = new BalanceResponse();
        int i = 33;
        int quantityBefore = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBase = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBase = accountRepository.findById(2000L).get();
        transferService.operation(accountEntitySender, accountEntityRecipient, BigDecimal.valueOf(i), balanceResponse);
        int quantityAfter = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBaseAfter = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBaseAfter = accountRepository.findById(2000L).get();
        assertEquals(accountEntitySenderInBase.getBalance().subtract(BigDecimal.valueOf(i)), accountEntitySenderInBaseAfter.getBalance());
        assertEquals(accountEntityRecipientInBase.getBalance().add(BigDecimal.valueOf(i)), accountEntityRecipientInBaseAfter.getBalance());
        assertEquals(quantityBefore + 1, quantityAfter);

        //Тест на отсутствие записи в базу.
        AccountEntity accountEntitySender1 = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipient1 = accountRepository.findById(2000L).get();
        BalanceResponse balanceResponse1 = new BalanceResponse();
        int i1 = 66;
        int quantityBefore1 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBase1 = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBase1 = accountRepository.findById(2000L).get();
        //Специально не указываем тип валюты для возникновения Exception.
        accountEntityRecipient1.setTypeCurrency(null);
        Throwable thrown = assertThrows(Exception.class, () -> {
            transferService.operation(accountEntitySender1, accountEntityRecipient1, BigDecimal.valueOf(i1), balanceResponse1);
        });
        int quantityAfter1 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBaseAfter1 = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBaseAfter1 = accountRepository.findById(2000L).get();
        assertNotEquals(accountEntitySenderInBase1.getBalance().subtract(BigDecimal.valueOf(i1)), accountEntitySenderInBaseAfter1.getBalance());
        assertNotEquals(accountEntityRecipientInBase1.getBalance().add(BigDecimal.valueOf(i1)), accountEntityRecipientInBaseAfter1.getBalance());
        assertEquals(quantityBefore1 , quantityAfter1);
    }
}