package ru.easybot.easybottest.service;

import ru.easybot.easybottest.dto.HddDTO;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, описывющий бизнес-логику по работе с жесткими дисками
 *
 * @author Ilya Kaltygin
 */
public interface HddService {

    /**
     * Получить список всех жестких дисков
     *
     * @return список жестких дисков
     */
    List<HddDTO> findAll();

    /**
     * Получить жесткий диск по идентификатору
     *
     * @param id идентификатор жесткого диска
     * @return Optional.of(hdd) если жесткий диск по заданному идентификатору найден, иначе Optional.empty()
     */
    Optional<HddDTO> findById(int id);

    /**
     * Сохранить жесткий диск
     *
     * @param hddDTO жесткий диск
     * @return сохраненный жесткий диск
     */
    HddDTO create(HddDTO hddDTO);

    /**
     * Обновить жесткий диск
     *
     * @param hddDTO жесткий диск
     * @param id     идентификатор жесткого диска
     * @return true если жесткий диск обновлен успешно, иначе false
     */
    boolean update(HddDTO hddDTO, int id);
}
