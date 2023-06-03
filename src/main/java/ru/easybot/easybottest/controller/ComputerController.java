package ru.easybot.easybottest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.dto.ComputerDTO;
import ru.easybot.easybottest.service.ComputerService;

import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@Tag(name = "ComputerController", description = "Контроллер, выполняющий обработку входящих запросов связанных с компьютерами")
@RestController
@RequestMapping("api/v1/computer")
@AllArgsConstructor
public class ComputerController {

    /**
     * Сервис по работе с компьютерами
     */
    private final ComputerService computerService;

    @Operation(
            summary = "Получить список всех компьютеров",
            description = "Метод возвращает список всех компьютеров, имеющихся в базе данных")
    @GetMapping("/")
    public List<ComputerDTO> findAll() {
        return computerService.findAll();
    }

    @Operation(
            summary = "Получить компьютер по идентификатору",
            description = """
                    Если компьютер по заданному идентификатору найден, то метод возвращает объект типа
                    ResponseEntity<ComputerDTO>, содержащий объект типа ComputerDTO, и статус ответа OK.
                    Если по заданному идентификатору компьютер не найден, то выбрасывается исключение
                    ResponseStatusException со статусом NOT_FOUND и сообщением, что компьютер с таким идентификатором не найден.
                    """
    )
    @GetMapping("/{id}")
    public ResponseEntity<ComputerDTO> findById(@PathVariable int id) {
        var computerDTO = computerService.findById(id);
        if (computerDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The computer with the specified ID was not found.");
        }
        return new ResponseEntity<>(computerDTO.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить компьютер",
            description = """
                    Метод сохранят компьютер в базу данных и возвращает объект типа
                    ResponseEntity<ComputerDTO>, содержащий созданный объект типа ComputerDTO и статус ответа CREATED
                    """
    )
    @PostMapping("/")
    public ResponseEntity<ComputerDTO> create(@RequestBody ComputerDTO computerDTO) {
        return new ResponseEntity<>(
                computerService.create(computerDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Обновить компьютер",
            description = """
                    Если обновление компьютера в базе данных будет успешным,
                    то метод вернет объект типа ResponseEntity<Void>, содержащий статус ответа OK.
                    Иначе метод выбросит исключение ResponseStatusException со статусом ответа BAD_REQUEST
                    и сообщением о том, что не удалось обновить компьютер
                    """
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Validated @RequestBody ComputerDTO computerDTO) {
        var update = computerService.update(computerDTO, id);
        if (!update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update computer");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
