package ru.easybot.easybottest.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.easybot.easybottest.dto.MonitorDTO;
import ru.easybot.easybottest.model.Monitor;
import ru.easybot.easybottest.repository.MonitorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с мониторами
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class MonitorServiceImpl implements MonitorService {

    /**
     * Хранилище мониторов
     */
    private final MonitorRepository monitorRepository;

    /**
     * Получить список всех мониторов
     *
     * @return список мониторов
     */
    @Override
    public List<MonitorDTO> findAll() {
        return monitorRepository.findAll().stream()
                .map(monitor -> new ModelMapper().map(monitor, MonitorDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить монитор по идентификатору
     *
     * @param id идентификатор монитора
     * @return Optional.of(monitor) если монитор по заданному идентификатору найден, иначе Optional.empty()
     */
    @Override
    public Optional<MonitorDTO> findById(int id) {
        return monitorRepository.findById(id)
                .map(monitor -> new ModelMapper().map(monitor, MonitorDTO.class));
    }

    /**
     * Сохранить монитор
     *
     * @param monitorDTO монитор
     * @return сохраненный монитор
     */
    @Override
    public MonitorDTO create(MonitorDTO monitorDTO) {
        var monitorFromDTO = new ModelMapper().map(monitorDTO, Monitor.class);
        var savedMonitor = monitorRepository.save(monitorFromDTO);
        return new ModelMapper().map(savedMonitor, MonitorDTO.class);
    }

    /**
     * Обновить монитор
     *
     * @param monitorDTO монитор
     * @param id         идентификатор монитора
     * @return true если монитор обновлен успешно, иначе false
     */
    @Override
    public boolean update(MonitorDTO monitorDTO, int id) {
        var monitorFromDTO = new ModelMapper().map(monitorDTO, Monitor.class);
        monitorFromDTO.setId(id);
        boolean result = false;
        if (monitorRepository.existsById(id)) {
            monitorRepository.save(monitorFromDTO);
            result = true;
        }
        return result;
    }
}
