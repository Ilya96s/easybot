package ru.easybot.easybottest.service;

import ru.easybot.easybottest.dto.LaptopDTO;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, описывющий бизнес-логику по работе с ноутбуками
 *
 * @author Ilya Kaltygin
 */
public interface LaptopService {

    /**
     * Получить список всех ноутбуков
     *
     * @return список ноутбуков
     */
    List<LaptopDTO> findAll();

    /**
     * Получить ноутбук по идентификатору
     *
     * @param id идентификатор ноутбука
     * @return Optional.of(laptop) если ноутбук по заданному идентификатору найден, иначе Optional.empty()
     */
    Optional<LaptopDTO> findById(int id);

    /**
     * Сохранить ноутбук
     *
     * @param laptopDTO ноутбук
     * @return сохраненный ноутбук
     */
    LaptopDTO create(LaptopDTO laptopDTO);

    /**
     * Обновить ноутбук
     *
     * @param laptopDTO ноутбук
     * @param id        идентификатор ноутбука
     * @return true если ноутбук обновлен успешно, иначе false
     */
    boolean update(LaptopDTO laptopDTO, int id);
}
