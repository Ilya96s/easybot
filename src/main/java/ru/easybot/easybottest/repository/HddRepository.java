package ru.easybot.easybottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.easybot.easybottest.model.Hdd;

/**
 * Хранилище жестких дисков
 *
 * @author Ilya Kaltygin
 */
public interface HddRepository extends JpaRepository<Hdd, Integer> {
}
