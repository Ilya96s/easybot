package ru.easybot.easybottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.easybot.easybottest.model.Laptop;

/**
 * Хранилище ноутбуков
 *
 * @author Ilya Kaltygin
 */
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
}
