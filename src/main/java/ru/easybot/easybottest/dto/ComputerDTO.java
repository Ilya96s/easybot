package ru.easybot.easybottest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author Ilya Kaltygin
 */
@Schema(description = "Модель данных, описывающая компьютер")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComputerDTO {

    @Schema(description = "Серийный номер")
    @NotBlank(message = "Series number must be not empty")
    private String serialNumber;

    @Schema(description = "Производитель")
    @NotBlank(message = "Manufacturer must be not empty")
    private String manufacturer;

    @Schema(description = "Цена")
    @Positive(message = "Price cannot be less than 0")
    private int price;

    @Schema(description = "Кол-во единиц продукции на складе")
    @Positive(message = "The quantity of the product cannot be less than 0")
    private int quantity;

    @Schema(description = "Тип компьютера")
    @NotBlank(message = "Type must be not null")
    private String type;
}
