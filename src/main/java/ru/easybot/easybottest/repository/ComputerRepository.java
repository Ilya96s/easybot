package ru.easybot.easybottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.easybot.easybottest.model.Computer;

/**
 * Хранилище компьютеров
 *
 * @author Ilya Kaltygin
 */
public interface ComputerRepository extends JpaRepository<Computer, Integer> {
}
