package ru.easybot.easybottest.service;

import ru.easybot.easybottest.dto.ComputerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, описывющий бизнес-логику по работе с компьютерами
 *
 * @author Ilya Kaltygin
 */
public interface ComputerService {

    /**
     * Получить список всех компьютеров
     *
     * @return список компьютеров
     */
    List<ComputerDTO> findAll();

    /**
     * Получить компьютер по идентификатору
     *
     * @param id идентификатор компьютера
     * @return Optional.of(computer) если компьютер по заданному идентификатору найден, иначе Optional.empty()
     */
    Optional<ComputerDTO> findById(int id);

    /**
     * Сохранить компьютер
     *
     * @param computer компьютер
     * @return сохраненный компьютер
     */
    ComputerDTO create(ComputerDTO computer);

    /**
     * Обновить компьютер
     *
     * @param computer компьютер
     * @param id   идентификатор компьютера
     * @return true если компьютер обновлен успешно, иначе false
     */
    boolean update(ComputerDTO computer, int id);
}
