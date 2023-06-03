package ru.easybot.easybottest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author Ilya Kaltygin
 */
@Schema(description = "Модель данных, описывабщая ноутбук")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LaptopDTO {

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

    @Schema(description = "Размер")
    @Min(value = 13, message = "Minimum laptop size should be 13")
    @Max(value = 17, message = "Maximum laptop size be 17")
    private int size;
}
