package ru.easybot.easybottest.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.easybot.easybottest.dto.LaptopDTO;
import ru.easybot.easybottest.model.Laptop;
import ru.easybot.easybottest.repository.LaptopRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с ноутбуками
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class LaptopServiceImpl implements LaptopService {

    /**
     * Хранилище ноутбуков
     */
    private final LaptopRepository laptopRepository;

    /**
     * Получить список всех ноутбуков
     *
     * @return список жноутбуков
     */
    @Override
    public List<LaptopDTO> findAll() {
        return laptopRepository.findAll().stream()
                .map(laptop -> new ModelMapper().map(laptop, LaptopDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить ноутбук по идентификатору
     *
     * @param id идентификатор ноутбука
     * @return Optional.of(laptop) если ноутбук по заданному идентификатору найден, иначе Optional.empty()
     */
    @Override
    public Optional<LaptopDTO> findById(int id) {
        return laptopRepository.findById(id)
                .map(laptop -> new ModelMapper().map(laptop, LaptopDTO.class));
    }

    /**
     * Сохранить ноутбук
     *
     * @param laptopDTO ноутбук
     * @return сохраненный ноутбук
     */
    @Override
    public LaptopDTO create(LaptopDTO laptopDTO) {
        var laptopFromDTO = new ModelMapper().map(laptopDTO, Laptop.class);
        var savedLaptop = laptopRepository.save(laptopFromDTO);
        return new ModelMapper().map(savedLaptop, LaptopDTO.class);
    }

    /**
     * Обновить жесткий диск
     *
     * @param laptopDTO ноутбук
     * @param id     идентификатор ноутбука
     * @return true если ноутбук обновлен успешно, иначе false
     */
    @Override
    public boolean update(LaptopDTO laptopDTO, int id) {
        var laptopFromDTO = new ModelMapper().map(laptopDTO, Laptop.class);
        laptopFromDTO.setId(id);
        boolean result = false;
        if (laptopRepository.existsById(id)) {
            laptopRepository.save(laptopFromDTO);
            result = true;
        }
        return result;
    }
}
