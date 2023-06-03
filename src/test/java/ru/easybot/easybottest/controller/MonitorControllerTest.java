package ru.easybot.easybottest.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.easybot.easybottest.EasybotTestApplication;
import ru.easybot.easybottest.dto.MonitorDTO;
import ru.easybot.easybottest.service.MonitorService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты для контроллера MonitorController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = EasybotTestApplication.class)
@AutoConfigureMockMvc
class MonitorControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private MonitorService monitorService;

    /**
     * Тест на метод findAll
     */
    @Test
    void whenFindAllThenShouldBeReturnListOfComputers() throws Exception {
        var monitor1 = MonitorDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .diagonal(22)
                .build();

        var monitor2 = MonitorDTO.builder()
                .serialNumber("SERIAL2")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(200)
                .diagonal(22)
                .build();

        var monitor3 = MonitorDTO.builder()
                .serialNumber("SERIAL3")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(155)
                .diagonal(22)
                .build();

        when(monitorService.findAll()).thenReturn(List.of(monitor1, monitor2, monitor3));

        mockMvc.perform(get("/api/v1/monitor/"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                {
                                    "serialNumber": "SERIAL",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 10,
                                    "diagonal": 22
                                },
                                {
                                    "serialNumber": "SERIAL2",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 200,
                                    "diagonal": 22
                                },
                                {
                                    "serialNumber": "SERIAL3",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 155,
                                    "diagonal": 22
                                }
                                ]
                                """)
                );
    }

    /**
     * Тест на метод FindById(int id). Когда монитор по заданному идентификатору найден.
     */
    @Test
    void whenFindByIdThenShouldBeReturnComputer() throws Exception {
        var monitor = MonitorDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .diagonal(22)
                .build();

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(monitorService.findById(argumentCaptor.capture())).thenReturn(Optional.of(monitor));

        mockMvc.perform(get("/api/v1/monitor/1"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "serialNumber": "SERIAL",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 10,
                                    "diagonal": 22
                                }
                                """)
                );

        verify(monitorService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод FindById(int id). Когда монитор по заданному идентификатору не найден.
     */
    @Test
    void whenComputerNotFoundById() throws Exception {
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(monitorService.findById(argumentCaptor.capture())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/monitor/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(monitorService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод Save(MonitorDTO monitor). Когда добавление монитора прошло успешно
     */
    @Test
    void whenSuccessfulCreateComputer() throws Exception {
        var savedMonitor = MonitorDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .diagonal(22)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("diagonal", 22);

        ArgumentCaptor<MonitorDTO> argumentCaptor = ArgumentCaptor.forClass(MonitorDTO.class);
        when(monitorService.create(argumentCaptor.capture())).thenReturn(savedMonitor);

        mockMvc.perform(post("/api/v1/monitor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "serialNumber": "SERIAL",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 10,
                                    "diagonal": 22
                                }
                                """)
                );

        verify(monitorService).create(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(savedMonitor));
    }

    /**
     * Тест на метод save(MonitorDTO monitor). Когда передали некорректные данные: не указали цену и остаток товара
     */
    @Test
    void whenSaveFails() throws Exception {
        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("diagonal", 22);

        mockMvc.perform(post("/api/v1/monitor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Тест на метод update(MonitorDTO monitor). Когда обновление прошло успешно
     */
    @Test
    void whenUpdateIsSuccessfulThenShouldReturnOk() throws Exception {

        var updatedMonitor = MonitorDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .diagonal(22)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("diagonal", 22);

        ArgumentCaptor<MonitorDTO> argumentCaptor = ArgumentCaptor.forClass(MonitorDTO.class);
        when(monitorService.update(argumentCaptor.capture(), eq(1))).thenReturn(true);

        mockMvc.perform(put("/api/v1/monitor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(monitorService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedMonitor));
    }

    /**
     * Тест на метод update(MonitorDTO monitor). Когда обновление прошло неудачно
     */
    @Test
    void whenUpdateFailsThenShouldBeReturnResponseStatusException() throws Exception {

        var updatedMonitor = MonitorDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .diagonal(22)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("diagonal", 22);

        ArgumentCaptor<MonitorDTO> argumentCaptor = ArgumentCaptor.forClass(MonitorDTO.class);
        when(monitorService.update(argumentCaptor.capture(), eq(1))).thenReturn(false);

        mockMvc.perform(put("/api/v1/monitor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(monitorService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedMonitor));
    }
}