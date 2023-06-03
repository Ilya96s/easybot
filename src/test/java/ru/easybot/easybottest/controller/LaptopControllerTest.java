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
import ru.easybot.easybottest.dto.LaptopDTO;
import ru.easybot.easybottest.service.LaptopService;

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
 * Тесты для контроллера LaptopController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = EasybotTestApplication.class)
@AutoConfigureMockMvc
class LaptopControllerTest {


    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private LaptopService laptopService;

    /**
     * Тест на метод findAll
     */
    @Test
    void whenFindAllThenShouldBeReturnListOfComputers() throws Exception {
        var laptop1 = LaptopDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .size(13)
                .build();

        var laptop2 = LaptopDTO.builder()
                .serialNumber("SERIAL2")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(200)
                .size(13)
                .build();

        var laptop3 = LaptopDTO.builder()
                .serialNumber("SERIAL3")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(155)
                .size(13)
                .build();

        when(laptopService.findAll()).thenReturn(List.of(laptop1, laptop2, laptop3));

        mockMvc.perform(get("/api/v1/laptop/"))
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
                                    "size": 13
                                },
                                {
                                    "serialNumber": "SERIAL2",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 200,
                                    "size": 13
                                },
                                {
                                    "serialNumber": "SERIAL3",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 155,
                                    "size": 13
                                }
                                ]
                                """)
                );
    }

    /**
     * Тест на метод FindById(int id). Когда ноутбук по заданному идентификатору найден.
     */
    @Test
    void whenFindByIdThenShouldBeReturnComputer() throws Exception {
        var laptop1 = LaptopDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .size(13)
                .build();

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(laptopService.findById(argumentCaptor.capture())).thenReturn(Optional.of(laptop1));

        mockMvc.perform(get("/api/v1/laptop/1"))
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
                                    "size": 13
                                }
                                """)
                );

        verify(laptopService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод FindById(int id). Когда ноутбук по заданному идентификатору не найден.
     */
    @Test
    void whenComputerNotFoundById() throws Exception {
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(laptopService.findById(argumentCaptor.capture())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/laptop/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(laptopService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод Save(LaptopDTO laptop). Когда добавление ноутбука прошло успешно
     */
    @Test
    void whenSuccessfulCreateComputer() throws Exception {
        var savedLaptop = LaptopDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .size(13)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("size", 13);

        ArgumentCaptor<LaptopDTO> argumentCaptor = ArgumentCaptor.forClass(LaptopDTO.class);
        when(laptopService.create(argumentCaptor.capture())).thenReturn(savedLaptop);

        mockMvc.perform(post("/api/v1/laptop/")
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
                                    "size": 13
                                }
                                """)
                );

        verify(laptopService).create(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(savedLaptop));
    }

    /**
     * Тест на метод save(LaptopDTO laptop). Когда передали некорректные данные: не указали цену и остаток товара
     */
    @Test
    void whenSaveFails() throws Exception {
        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("size", 13);

        mockMvc.perform(post("/api/v1/laptop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Тест на метод update(LaptopDTO laptop). Когда обновление прошло успешно
     */
    @Test
    void whenUpdateIsSuccessfulThenShouldReturnOk() throws Exception {

        var updatedLaptop = LaptopDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .size(13)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("size", 13);

        ArgumentCaptor<LaptopDTO> argumentCaptor = ArgumentCaptor.forClass(LaptopDTO.class);
        when(laptopService.update(argumentCaptor.capture(), eq(1))).thenReturn(true);

        mockMvc.perform(put("/api/v1/laptop/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(laptopService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedLaptop));
    }

    /**
     * Тест на метод update(LaptopDTO laptop). Когда обновление прошло неудачно
     */
    @Test
    void whenUpdateFailsThenShouldBeReturnResponseStatusException() throws Exception {

        var updatedLaptop = LaptopDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .size(13)
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("size", 13);

        ArgumentCaptor<LaptopDTO> argumentCaptor = ArgumentCaptor.forClass(LaptopDTO.class);
        when(laptopService.update(argumentCaptor.capture(), eq(1))).thenReturn(false);

        mockMvc.perform(put("/api/v1/laptop/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(laptopService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedLaptop));
    }

}