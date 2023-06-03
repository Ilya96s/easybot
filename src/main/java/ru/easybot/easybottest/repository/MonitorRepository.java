package ru.easybot.easybottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.easybot.easybottest.model.Monitor;

/**
 * Хранилище мониторов
 *
 * @author Ilya Kaltygin
 */
public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
}
