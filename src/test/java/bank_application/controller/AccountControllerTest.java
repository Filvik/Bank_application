package bank_application.controller;

import bank_application.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Mock
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBalance() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBalance_for_id/1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1200));

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
    @Test
    void getOperationList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api//getOperationList_for_id/10000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"description\":\"Ошибка при вводе ID пользователя!\"}]"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api//getOperationList_for_id/1000?dataStart=16&dataStop=15"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"description\":\"Некорректно задан временной диапазон!\"}]"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api//getOperationList_for_id/1000?dataStart=2&dataStop=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data").value(2))
                .andExpect(jsonPath("$[0].type_operation").value(1))
                .andExpect(jsonPath("$[0].sum").value(12))
                .andExpect(jsonPath("$[1].data").value(3))
                .andExpect(jsonPath("$[1].type_operation").value(2))
                .andExpect(jsonPath("$[1].sum").value(13));

        mockMvc.perform(MockMvcRequestBuilders.get("/api//getOperationList_for_id/1000?dataStart=222&dataStop=333"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}