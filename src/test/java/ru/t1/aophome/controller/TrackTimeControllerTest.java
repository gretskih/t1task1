package ru.t1.aophome.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TrackTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Test() throws Exception {

        /**
         * Очистка таблицы url - метод deleteAll()
         */
        this.mockMvc.perform(delete("/url/delete/all"));
        //.andDo(print());

        Thread.sleep(200);

        String request1 = "{ \"url\": " +
                "\"yandex.ru\" }";
        String request2 = "{ \"url\": " +
                "\"mail.ru\" }";
        String request3 = "{ \"url\": " +
                "\"nalog.ru\" }";

        /**
         * Метод addUrl()
         */
        this.mockMvc.perform(post("/url/code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                //.andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/url/code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                //.andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/url/code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request3))
                //.andDo(print())
                .andExpect(status().isOk());

        /**
         * Выполнение метода getAll()
         */
        this.mockMvc.perform(get("/url/all"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        /**
         * Выполнение метода getUrlByCode()
         */
        this.mockMvc.perform(get("/url/SIuNTmvk3A"))
                //.andDo(print())
                .andExpect(status().is4xxClientError());

        /**
         * Выполнение метода deleteByCode()
         */
        this.mockMvc.perform(delete("/url/delete/SIuNTmvk3A"));
        //.andDo(print());

        String request4 = "{ \"name\": " +
                "\"addUrl\" }";

        /**
         * Суммарное время выполнения метода addUrl()
         */
        this.mockMvc.perform(post("/time/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request4))
                .andDo(print())
                .andExpect(status().isOk());

        /**
         * Список всех записей о времени выполнения по имени метода addUrl()
         */
        this.mockMvc.perform(post("/time/method")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request4))
                .andDo(print())
                .andExpect(status().isOk());

        /**
         * Число вызовов метода addUrl()
         */
        this.mockMvc.perform(post("/time/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request4))
                .andDo(print())
                .andExpect(status().isOk());

        /**
         * Среднее время выполнения метода addUrl()
         */
        this.mockMvc.perform(post("/time/avg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request4))
                .andDo(print())
                .andExpect(status().isOk());

        String request5 = "{ \"name\": " +
                "\"get\" }";

        /**
         * Среднее время выполнения группы методов get*()
         */
        this.mockMvc.perform(post("/time/avg/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request5))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
