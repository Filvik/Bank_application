package bank_application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
