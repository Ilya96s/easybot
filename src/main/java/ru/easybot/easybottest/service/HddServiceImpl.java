package ru.easybot.easybottest.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.easybot.easybottest.dto.HddDTO;
import ru.easybot.easybottest.model.Hdd;
import ru.easybot.easybottest.repository.HddRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с жесткими дисками
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class HddServiceImpl implements HddService {

    /**
     * Хранилище жестких дисков
     */
    private final HddRepository hddRepository;

    /**
     * Получить список всех жестких дисков
     *
     * @return список жестких дисков
     */
    @Override
    public List<HddDTO> findAll() {
        return hddRepository.findAll().stream()
                .map(hdd -> new ModelMapper().map(hdd, HddDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить жесткий диск по идентификатору
     *
     * @param id идентификатор жесткого диска
     * @return Optional.of(hdd) если жесткий диск по заданному идентификатору найден, иначе Optional.empty()
     */
    @Override
    public Optional<HddDTO> findById(int id) {
        return hddRepository.findById(id)
                .map(hdd -> new ModelMapper().map(hdd, HddDTO.class));
    }

    /**
     * Сохранить жесткий диск
     *
     * @param hddDTO жесткий диск
     * @return сохраненный жесткий диск
     */
    @Override
    public HddDTO create(HddDTO hddDTO) {
        var hddFromDTO = new ModelMapper().map(hddDTO, Hdd.class);
        var savedHdd = hddRepository.save(hddFromDTO);
        return new ModelMapper().map(savedHdd, HddDTO.class);
    }

    /**
     * Обновить жесткий диск
     *
     * @param hddDTO жесткий диск
     * @param id     идентификатор жесткого диска
     * @return true если жесткий диск обновлен успешно, иначе false
     */
    @Override
    public boolean update(HddDTO hddDTO, int id) {
        var hddFromDTO = new ModelMapper().map(hddDTO, Hdd.class);
        hddFromDTO.setId(id);
        boolean result = false;
        if (hddRepository.existsById(id)) {
            hddRepository.save(hddFromDTO);
            result = true;
        }
        return result;
    }
}
