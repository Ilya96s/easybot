package ru.easybot.easybottest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.dto.LaptopDTO;
import ru.easybot.easybottest.service.LaptopService;

import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@Tag(name = "LaptopController", description = "Контроллер, выполняющий обработку входящих запросов связанных с ноутбуками")
@RestController
@RequestMapping("api/v1/laptop")
@AllArgsConstructor
public class LaptopController {

    /**
     * Сервис по работе с жесткими дисками
     */
    private final LaptopService laptopService;

    @Operation(
            summary = "Получить список всех ноутбуков",
            description = "Метод возвращает список всех ноутбуков, имеющихся в базе данных")
    @GetMapping("/")
    public List<LaptopDTO> findAll() {
        return laptopService.findAll();
    }

    @Operation(
            summary = "Получить ноутбук по идентификатору",
            description = """
                    Если ноутбук по заданному идентификатору найден, то метод возвращает объект типа
                    ResponseEntity<LaptopDTO>, содержащий объект типа LaptopDTO, и статус ответа OK.
                    Если по заданному идентификатору ноутбук не найден, то выбрасывается исключение
                    ResponseStatusException со статусом NOT_FOUND и сообщением, что ноутбук с таким идентификатором не найден.
                    """
    )
    @GetMapping("/{id}")
    public ResponseEntity<LaptopDTO> findById(@PathVariable int id) {
        var laptopDTO = laptopService.findById(id);
        if (laptopDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The hdd with the specified ID was not found.");
        }
        return new ResponseEntity<>(laptopDTO.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить ноутбук",
            description = """
                    Метод сохранят ноутбук в базу данных и возвращает объект типа
                    ResponseEntity<LaptopDTO>, содержащий созданный объект типа LaptopDTO и статус ответа CREATED"
                    """
    )
    @PostMapping("/")
    public ResponseEntity<LaptopDTO> create(@Validated @RequestBody LaptopDTO laptopDTO) {
        return new ResponseEntity<>(
                laptopService.create(laptopDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Обновить ноутбук",
            description = """
                    Если обновление ноутбука в базе данных будет успешным,
                    то метод вернет объект типа ResponseEntity<Void>, содержащий статус ответа OK.
                    Иначе метод выбросит исключение ResponseStatusException со статусом ответа BAD_REQUEST
                    и сообщением о том, что не удалось обновить ноутбук"
                    """
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Validated @RequestBody LaptopDTO laptopDTO) {
        var update = laptopService.update(laptopDTO, id);
        if (!update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update hdd");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
