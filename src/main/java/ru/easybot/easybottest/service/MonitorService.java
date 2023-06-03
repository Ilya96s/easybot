package ru.easybot.easybottest.service;

import ru.easybot.easybottest.dto.MonitorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, описывющий бизнес-логику по работе с мониторами
 *
 * @author Ilya Kaltygin
 */
public interface MonitorService {

    /**
     * Получить список всех мониторов
     *
     * @return список мониторов
     */
    List<MonitorDTO> findAll();

    /**
     * Получить монитор по идентификатору
     *
     * @param id идентификатор монитора
     * @return Optional.of(monitor) если монитор по заданному идентификатору найден, иначе Optional.empty()
     */
    Optional<MonitorDTO> findById(int id);

    /**
     * Сохранить монитор
     *
     * @param monitorDTO монитор
     * @return сохраненный монитор
     */
    MonitorDTO create(MonitorDTO monitorDTO);

    /**
     * Обновить монитор
     *
     * @param monitorDTO монитор
     * @param id         идентификатор монитора
     * @return true если монитор обновлен успешно, иначе false
     */
    boolean update(MonitorDTO monitorDTO, int id);
}
