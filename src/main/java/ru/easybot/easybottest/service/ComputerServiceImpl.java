package ru.easybot.easybottest.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.easybot.easybottest.dto.ComputerDTO;
import ru.easybot.easybottest.model.Computer;
import ru.easybot.easybottest.model.Type;
import ru.easybot.easybottest.repository.ComputerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с компьютерами
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class ComputerServiceImpl implements ComputerService {

    /**
     * Хранилище компьютеров
     */
    private final ComputerRepository computerRepository;

    /**
     * Получить список всех компьютеров
     *
     * @return список компьютеров
     */
    @Override
    public List<ComputerDTO> findAll() {
        return computerRepository.findAll().stream()
                .map(computer -> new ModelMapper().map(computer, ComputerDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить компьютер по идентификатору
     *
     * @param id идентификатор компьютера
     * @return Optional.of(computer) если компьютер по заданному идентификатору найден, иначе Optional.empty()
     */
    @Override
    public Optional<ComputerDTO> findById(int id) {
        return computerRepository.findById(id)
                .map(computer -> new ModelMapper().map(computer, ComputerDTO.class));
    }

    /**
     * Сохранить компьютер
     *
     * @param computer компьютер
     * @return сохраненный компьютер
     */
    @Override
    public ComputerDTO create(ComputerDTO computer) {
        checkComputerType(computer);
        var computerFromDTO = new ModelMapper().map(computer, Computer.class);
        var savedComputer = computerRepository.save(computerFromDTO);
        return new ModelMapper().map(savedComputer, ComputerDTO.class);
    }

    /**
     * Обновить компьютер
     *
     * @param computer компьютер
     * @param id       идентификатор компьютера
     * @return true если компьютер обновлен успешно, иначе false
     */
    @Override
    public boolean update(ComputerDTO computer, int id) {
        checkComputerType(computer);
        var computerFromDTO = new ModelMapper().map(computer, Computer.class);
        computerFromDTO.setId(id);
        boolean result = false;
        if (computerRepository.existsById(id)) {
            computerRepository.save(computerFromDTO);
            result = true;
        }
        return result;
    }

    /**
     *   Проверкить тип компьютера. Если тип компьютера не совпадает с одним из типов (ONOBLOCK, NETTOP, DESKTOP),
     *   то выбрасывается исключение  ResponseStatusException
     * @param computer компютер
     */
    private void checkComputerType(ComputerDTO computer) {
        var count = Arrays.stream(Type.values()).filter(type -> type.name().equals(computer.getType())).count();
        if (count == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("The computer must be of type: MONOBLOCK, NETTOP, DESKTOP. But you indicated %s",
                            computer.getType()));
        }
    }
}
