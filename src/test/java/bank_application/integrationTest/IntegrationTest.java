package bank_application.integrationTest;

import bank_application.BankApplication;
import bank_application.model.AccountEntity;
import bank_application.model.BalanceResponse;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import bank_application.service.AccountService;
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
    }
}
