package bank_application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransferMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void transferMoney() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/2000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/2000/-0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Некорректная сумма перевода!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1001/2000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отсутствует отправитель!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/12000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отсутствует получатель!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/1000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отправитель и получатель не могут иметь одинаковый ID!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/3000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Разные типы валют!Перевод не допустим!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/TransferMoney_for/1000/2000/10000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Превышена возможная сумма перевода!"));
    }
}