package ru.t1.aophome.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Code Api",
                description = "Cервис выполняет преобразование ссылки в уникальный код, по которому можно получить ассоциированную ссылку. " +
                        "Ведется статистика по обработке запросов.", version = "1.0.0",
                contact = @Contact(
                        name = "Anatoly",
                        email = "gretskih@mail.ru"
                )
        )
)
public class OpenApiConfig {
}
