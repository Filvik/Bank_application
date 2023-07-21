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
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBalance() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBalance_for_id/1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1000));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBalance_for_id/1001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(-1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBalance_for_id/1001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отсутствует данный пользователь!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBalance_for_id/1test"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void takeMoney() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/9999/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/9999/9999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отсутствует данный пользователь!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/1000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/1000/100000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Недостаточно средств на счёте!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/1000/-100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/1000/-100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Некорректное значение запрошенной суммы!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/takeMoney_for_id/test/test"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void putMoney() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/9999/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/9999/9999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Отсутствует данный пользователь!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/1000/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/1000/-100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/1000/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description_error").value("Некорректное значение суммы пополнения!"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/putMoney_for_id/test/test"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}