package ru.easybot.easybottest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.dto.HddDTO;
import ru.easybot.easybottest.service.HddService;

import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@Tag(name = "HddController", description = "Контроллер, выполняющий обработку входящих запросов связанных с жесткими дисками")
@RestController
@RequestMapping("api/v1/hdd")
@AllArgsConstructor
public class HddController {

    /**
     * Сервис по работе с жесткими дисками
     */
    private final HddService hddService;

    @Operation(
            summary = "Получить список всех жестких дисков",
            description = "Метод возвращает список всех жестких дисков, имеющихся в базе данных")
    @GetMapping("/")
    public List<HddDTO> findAll() {
        return hddService.findAll();
    }

    @Operation(
            summary = "Получить жесткий диск по идентификатору",
            description = "Если жесткий диск по заданному идентификатору найден, то метод возвращает объект типа " +
                    "ResponseEntity<HddDTO>, содержащий объект типа HddDTO, и статус ответа OK." +
                    "Если по заданному идентификатору жесткий диск не найден, то выбрасывается исключение " +
                    "ResponseStatusException со статусом NOT_FOUND и сообщением, что жесткий диск с таким идентификатором не найден."
    )
    @GetMapping("/{id}")
    public ResponseEntity<HddDTO> findById(@PathVariable int id) {
        var hddDTO = hddService.findById(id);
        if (hddDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The hdd with the specified ID was not found.");
        }
        return new ResponseEntity<>(hddDTO.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить жесткий диск",
            description = "Метод сохранят жесткий диск в базу данных и возвращает объект типа " +
                    "ResponseEntity<HddDTO>, содержащий созданный объект типа HddDTO и статус ответа CREATED"
    )
    @PostMapping("/")
    public ResponseEntity<HddDTO> create(@Validated @RequestBody HddDTO hddDTO) {
        return new ResponseEntity<>(
                hddService.create(hddDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Обновить жесткий диск",
            description = "Если обновление жесткого диска в базе данных будет успешным, " +
                    "то метод вернет объект типа ResponseEntity<Void>, содержащий статус ответа OK." +
                    "Иначе метод выбросит исключение ResponseStatusException со статусом ответа BAD_REQUEST " +
                    "и сообщением о том, что не удалось обновить жесткий диск"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Validated @RequestBody HddDTO hddDTO) {
        var update = hddService.update(hddDTO, id);
        if (!update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update hdd");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
