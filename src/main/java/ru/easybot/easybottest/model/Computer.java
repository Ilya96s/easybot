package ru.easybot.easybottest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных, описывающая компьютер
 *
 * @author Ilya Kaltygin
 */
@Entity
@Table(name = "computer")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Data
public class Computer {

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
     * Тип компьютера
     */
    @Enumerated(EnumType.STRING)
    private Type type;
}
