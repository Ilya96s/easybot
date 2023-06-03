package ru.easybot.easybottest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Модель данных, описывающая монитор
 *
 * @author Ilya Kaltygin
 */
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Data
public class Monitor {

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
     * Диагональ
     */
    private int diagonal;
}
