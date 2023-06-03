package ru.easybot.easybottest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


/**
 * SwaggerConfig - конфигурационный класс для Swagger
 *
 * @author Ilya Kaltytin
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Computer store API",
                description = "Shop selling computers and accessories", version = "1.0.0",
                contact = @Contact(
                        name = "Kaltygin Ilya",
                        email = "i.kaltygin@icloud.com",
                        url = "https://telegram.me/ilya96s"
                )
        )
)
@Configuration
public class SwaggerConfig {
}
