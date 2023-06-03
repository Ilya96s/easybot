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
import ru.easybot.easybottest.dto.HddDTO;
import ru.easybot.easybottest.service.HddService;

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
 * Тесты для контроллера HddController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = EasybotTestApplication.class)
@AutoConfigureMockMvc
class HddControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private HddService hddService;

    /**
     * Тест на метод findAll
     */
    @Test
    void whenFindAllThenShouldBeReturnListOfComputers() throws Exception {
        var hdd1 = HddDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .capacity(10)
                .build();

        var hdd2 = HddDTO.builder()
                .serialNumber("SERIAL2")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(200)
                .capacity(20)
                .build();

        var hdd3 = HddDTO.builder()
                .serialNumber("SERIAL3")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(155)
                .capacity(30)
                .build();

        when(hddService.findAll()).thenReturn(List.of(hdd1, hdd2, hdd3));

        mockMvc.perform(get("/api/v1/hdd/"))
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
                                    "capacity": 10
                                },
                                {
                                    "serialNumber": "SERIAL2",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 200,
                                    "capacity": 20
                                },
                                {
                                    "serialNumber": "SERIAL3",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 155,
                                    "capacity": 30
                                }
                                ]
                                """)
                );
    }

    /**
     * Тест на метод FindById(int id). Когда жесткий диск по заданному идентификатору найден.
     */
    @Test
    void whenFindByIdThenShouldBeReturnComputer() throws Exception {
        var hdd1 = HddDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .capacity(10)
                .build();

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(hddService.findById(argumentCaptor.capture())).thenReturn(Optional.of(hdd1));

        mockMvc.perform(get("/api/v1/hdd/1"))
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
                                    "capacity": 10
                                }
                                """)
                );

        verify(hddService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод FindById(int id). Когда жесткий диск по заданному идентификатору не найден.
     */
    @Test
    void whenComputerNotFoundById() throws Exception {
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(hddService.findById(argumentCaptor.capture())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/hdd/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(hddService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод Save(HddDTO hdd). Когда добавление жесткого диска прошло успешно
     */
    @Test
    void whenSuccessfulCreateComputer() throws Exception {
        var savedHdd = HddDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .capacity(10)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("capacity", 10);

        ArgumentCaptor<HddDTO> argumentCaptor = ArgumentCaptor.forClass(HddDTO.class);
        when(hddService.create(argumentCaptor.capture())).thenReturn(savedHdd);

        mockMvc.perform(post("/api/v1/hdd/")
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
                                    "capacity": 10
                                }
                                """)
                );

        verify(hddService).create(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(savedHdd));
    }

    /**
     * Тест на метод save(HddDTO hdd). Когда передали некорректные данные: не указали цену и остаток товара
     */
    @Test
    void whenSaveFails() throws Exception {
        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("capacity", 10);

        mockMvc.perform(post("/api/v1/hdd/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Тест на метод update(HddDTO hdd). Когда обновление прошло успешно
     */
    @Test
    void whenUpdateIsSuccessfulThenShouldReturnOk() throws Exception {

        var updatedHdd = HddDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .capacity(10)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("capacity", 10);

        ArgumentCaptor<HddDTO> argumentCaptor = ArgumentCaptor.forClass(HddDTO.class);
        when(hddService.update(argumentCaptor.capture(), eq(1))).thenReturn(true);

        mockMvc.perform(put("/api/v1/hdd/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(hddService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedHdd));
    }

    /**
     * Тест на метод update(HddDTO hdd). Когда обновление прошло неудачно
     */
    @Test
    void whenUpdateFailsThenShouldBeReturnResponseStatusException() throws Exception {

        var updatedHdd = HddDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .capacity(10)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("capacity", 10);

        ArgumentCaptor<HddDTO> argumentCaptor = ArgumentCaptor.forClass(HddDTO.class);
        when(hddService.update(argumentCaptor.capture(), eq(1))).thenReturn(false);

        mockMvc.perform(put("/api/v1/hdd/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(hddService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedHdd));
    }
}