package ru.t1.aophome.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность кода")
public record ReqCodeDto(String code) {
}
