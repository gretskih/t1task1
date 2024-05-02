package ru.t1.aophome.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность имени метода")
public record MethodNameDto(String name) {
}
