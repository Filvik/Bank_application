package bank_application.integrationTest;

import bank_application.BankApplication;
import bank_application.model.AccountEntity;
import bank_application.model.BalanceResponse;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import bank_application.service.AccountService;
import bank_application.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;

@SpringBootTest(classes= BankApplication.class)
@AutoConfigureMockMvc
public class IntegrationTest {
    @Mock
    private AccountService accountService;
    @Mock
    private TransferService transferService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationsRepository operationsRepository;

    @Test
    void generalTest() throws Exception {

        BalanceResponse balanceResponse = new BalanceResponse();
        int quantityBefore = operationsRepository.findAll().size();
        AccountEntity accountEntityInBase = accountRepository.findById(1000L).get();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/1000/50"));
        accountService.operation(accountRepository.findById(1000L).get(), BigDecimal.valueOf(50), 2, balanceResponse);
        int quantityAfter = operationsRepository.findAll().size();
        AccountEntity accountEntityInBaseAfter = accountRepository.findById(1000L).get();
        Assertions.assertEquals(accountEntityInBase.getBalance().subtract(BigDecimal.valueOf(50)), accountEntityInBaseAfter.getBalance());
        Assertions.assertEquals(quantityBefore + 1, quantityAfter);

        BalanceResponse balanceResponse2 = new BalanceResponse();
        int quantityBefore2 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBase2 = accountRepository.findById(1000L).get();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/1000/80"));
        accountService.operation(accountRepository.findById(1000L).get(), BigDecimal.valueOf(80), 1, balanceResponse2);
        int quantityAfter2 = operationsRepository.findAll().size();
        AccountEntity accountEntityInBaseAfter2 = accountRepository.findById(1000L).get();
        Assertions.assertEquals(accountEntityInBase2.getBalance().add(BigDecimal.valueOf(80)), accountEntityInBaseAfter2.getBalance());
        Assertions.assertEquals(quantityBefore2 + 1, quantityAfter2);

        int quantityBefore3 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBase = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBase = accountRepository.findById(2000L).get();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/2000/180"));
        int quantityAfter3 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBaseAfter = accountRepository.findById(1000L).get();
        AccountEntity accountEntityRecipientInBaseAfter = accountRepository.findById(2000L).get();
        Assertions.assertEquals(accountEntitySenderInBase.getBalance().subtract(BigDecimal.valueOf(180)), accountEntitySenderInBaseAfter.getBalance());
        Assertions.assertEquals(accountEntityRecipientInBase.getBalance().add(BigDecimal.valueOf(180)), accountEntityRecipientInBaseAfter.getBalance());
        Assertions.assertEquals(quantityBefore3 + 1, quantityAfter3);

        int quantityBefore4 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBase1 = accountRepository.findById(1000L).get();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/getBalance_for_id/1000"));
        int quantityAfter4 = operationsRepository.findAll().size();
        AccountEntity accountEntitySenderInBaseAfter1 = accountRepository.findById(1000L).get();
        Assertions.assertEquals(accountEntitySenderInBase1.getBalance(), accountEntitySenderInBaseAfter1.getBalance());
        Assertions.assertEquals(quantityBefore4, quantityAfter4);
    }
}
