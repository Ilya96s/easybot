package ru.easybot.easybottest.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler - глобальный обработчик исключений, возникших в контроллерах
 *
 * @author Ilya Kaltygin
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает все исключения MethodArgumentNotValidException, которые возникают в контроллере
     *
     * @param e исключение, которые было сгенерировано и перехваченоданным методом
     * @return возвращает ResponseEntity со статусом 400 и телом ответа, которое содержит список ошибок валидаций полей
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                f.getField(),
                                String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue())
                        ))
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        String[] parts = message.split("\'");
        String fieldName = parts[1];
        String value = parts[3];
        String bodyOfResponse = "Значение параметра " + fieldName + " (" + value + ") вызвало нарушение ограничения целостности базы данных.";
        return ResponseEntity.badRequest().body(bodyOfResponse);
    }
}
