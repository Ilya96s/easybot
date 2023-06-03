package ru.easybot.easybottest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.dto.MonitorDTO;
import ru.easybot.easybottest.service.MonitorService;

import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@Tag(name = "MonitorController", description = "Контроллер, выполняющий обработку входящих запросов связанных с мониторами")
@RestController
@RequestMapping("api/v1/monitor")
@AllArgsConstructor
public class MonitorController {

    /**
     * Сервис по работе с мониторами
     */
    private final MonitorService monitorService;

    @Operation(
            summary = "Получить список всех мониторов",
            description = "Метод возвращает список всех мониторов, имеющихся в базе данных")
    @GetMapping("/")
    public List<MonitorDTO> findAll() {
        return monitorService.findAll();
    }

    @Operation(
            summary = "Получить монитор по идентификатору",
            description = "Если монитор по заданному идентификатору найден, то метод возвращает объект типа " +
                    "ResponseEntity<MonitorDTO>, содержащий объект типа MonitorDTO, и статус ответа OK." +
                    "Если по заданному идентификатору монитор не найден, то выбрасывается исключение " +
                    "ResponseStatusException со статусом NOT_FOUND и сообщением, что монитор с таким идентификатором не найден."
    )
    @GetMapping("/{id}")
    public ResponseEntity<MonitorDTO> findById(
            @Parameter(description = "Идентификатор монитора")
            @PathVariable int id) {
        var monitorDTO = monitorService.findById(id);
        if (monitorDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The monitor with the specified ID was not found.");
        }
        return new ResponseEntity<>(monitorDTO.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить монитор",
            description = "Метод сохранят монитор в базу данных и возвращает объект типа " +
                    "ResponseEntity<MonitorDTO>, содержащий созданный объект типа MonitorDTO и статус ответа CREATED"
    )
    @PostMapping("/")
    public ResponseEntity<MonitorDTO> create(
            @Parameter(description = "Объект типа MonitorDTO, содержащий в себе данные переданные клиентом")
            @Validated @RequestBody MonitorDTO monitorDTO) {
        return new ResponseEntity<>(
                monitorService.create(monitorDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Обновить монитор",
            description = "Если обновление монитора в базе данных будет успешным, " +
                    "то метод вернет объект типа ResponseEntity<Void>, содержащий статус ответа OK." +
                    "Иначе метод выбросит исключение ResponseStatusException со статусом ответа BAD_REQUEST " +
                    "и сообщением о том, что не удалось обновить монитор"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Validated @RequestBody MonitorDTO monitorDTO) {
        var update = monitorService.update(monitorDTO, id);
        if (!update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update monitor");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
