package ru.easybot.easybottest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных, описывающая жесткий диск
 *
 * @author Ilya Kaltygin
 */
@Entity
@Table(name = "hdd")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Data
public class Hdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * НСерийный номер
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
     * Объем
     */
    private long capacity;
}
