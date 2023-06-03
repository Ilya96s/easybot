package ru.easybot.easybottest.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.EasybotTestApplication;
import ru.easybot.easybottest.dto.ComputerDTO;
import ru.easybot.easybottest.service.ComputerService;

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
 * Тесты для контроллера ComputerController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = EasybotTestApplication.class)
@AutoConfigureMockMvc
class ComputerControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private ComputerService computerService;

    /**
     * Тест на метод findAll
     */
    @Test
    void whenFindAllThenShouldBeReturnListOfComputers() throws Exception {
        var computer1 = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("NETTOP")
                .build();

        var computer2 = ComputerDTO.builder()
                .serialNumber("SERIAL2")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(200)
                .type("DESKTOP")
                .build();


        var computer3 = ComputerDTO.builder()
                .serialNumber("SERIAL3")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(155)
                .type("MONOBLOCK")
                .build();

        when(computerService.findAll()).thenReturn(List.of(computer1, computer2, computer3));

        mockMvc.perform(get("api/v1/computer/"))
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
                                    "type": "NETTOP"
                                },
                                {
                                    "serialNumber": "SERIAL2",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 200,
                                    "type": "DESKTOP"
                                },
                                {
                                    "serialNumber": "SERIAL3",
                                    "manufacturer": "MANUFACTURER1",
                                    "price": 10000,
                                    "quantity": 155,
                                    "type": "MONOBLOCK"
                                }
                                ]
                                """)
                );
    }

    /**
     * Тест на метод FindById(int id). Когда компьютер по заданному идентификатору найден.
     */
    @Test
    void whenFindByIdThenShouldBeReturnComputer() throws Exception {
        var computer1 = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("NETTOP")
                .build();

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(computerService.findById(argumentCaptor.capture())).thenReturn(Optional.of(computer1));

        mockMvc.perform(get("api/v1/computer/1"))
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
                                    "type": "NETTOP"
                                }
                                """)
                );

        verify(computerService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод FindById(int id). Когда компьютер по заданному идентификатору не найден.
     */
    @Test
    void whenComputerNotFoundById() throws Exception {
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(computerService.findById(argumentCaptor.capture())).thenReturn(Optional.empty());

        mockMvc.perform(get("api/v1/computer/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(computerService).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(1));
    }

    /**
     * Тест на метод Save(ComputerDTO computerDTO). Когда добавление компьютера прошло успешно
     */
    @Test
    void whenSuccessfulCreateComputer() throws Exception {
        var savedComputer = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("NETTOP")
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("type", "NETTOP");

        ArgumentCaptor<ComputerDTO> argumentCaptor = ArgumentCaptor.forClass(ComputerDTO.class);
        when(computerService.create(argumentCaptor.capture())).thenReturn(savedComputer);

        mockMvc.perform(post("api/v1/computer/")
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
                                    "type": "NETTOP"
                                }
                                """)
                );

        verify(computerService).create(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(savedComputer));
    }

    /**
     * Тест на метод save(ComputerDTO computer). Когда передали не верный тип компьютера
     */
    @Test
    void whenIncorrectTypeThenShouldBeReturnResponseStatusException() throws Exception {
        var savedComputer = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("test")
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("type", "test");

        ArgumentCaptor<ComputerDTO> argumentCaptor = ArgumentCaptor.forClass(ComputerDTO.class);
        when(computerService.create(argumentCaptor.capture())).thenThrow(
                new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("api/v1/computer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(computerService).create(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is(savedComputer));
    }

    /**
     * Тест на метод update(ComputerDTO computer). Когда обновление прошло успешно
     */
    @Test
    void whenUpdateIsSuccessfulThenShouldReturnOk() throws Exception {

        var updatedComputer = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("NETTOP")
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("type", "NETTOP");

        ArgumentCaptor<ComputerDTO> argumentCaptor = ArgumentCaptor.forClass(ComputerDTO.class);
        when(computerService.update(argumentCaptor.capture(), eq(1))).thenReturn(true);

        mockMvc.perform(put("api/v1/computer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(computerService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedComputer));
    }

    /**
     * Тест на метод update(ComputerDTO computer). Когда обновление прошло неудачно
     */
    @Test
    void whenUpdateFailsThenShouldBeReturnResponseStatusException() throws Exception {

        var updatedComputer = ComputerDTO.builder()
                .serialNumber("SERIAL")
                .manufacturer("MANUFACTURER1")
                .price(10000)
                .quantity(10)
                .type("NETTOP")
                .build();

        var jsonObject = new JSONObject();
        jsonObject.put("serialNumber", "SERIAL");
        jsonObject.put("manufacturer", "MANUFACTURER1");
        jsonObject.put("price", 10000);
        jsonObject.put("quantity", 10);
        jsonObject.put("type", "NETTOP");

        ArgumentCaptor<ComputerDTO> argumentCaptor = ArgumentCaptor.forClass(ComputerDTO.class);
        when(computerService.update(argumentCaptor.capture(), eq(1))).thenReturn(false);

        mockMvc.perform(put("api/v1/computer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(computerService).update(argumentCaptor.capture(), eq(1));

        assertThat(argumentCaptor.getValue(), is(updatedComputer));
    }
}