package ru.easybot.easybottest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных, описывающая ноутбук
 *
 * @author Ilya Kaltygin
 */
@Entity
@Table(name = "laptop")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Data
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Серийный номер
     */
    private String serialNumber;

    /**
     * Производитель
     */
    private String manufacturer;

    /**
     * Цена
     */
    private int price;

    /**
     * Кол-во единиц продукции на складе
     */
    private int quantity;

    /**
     * Размер
     */
    private int size;
}
