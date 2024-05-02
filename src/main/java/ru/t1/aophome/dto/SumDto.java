package ru.t1.aophome.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Сущность суммарного времени")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SumDto {
    private String nameMethod;
    private Long sumExecutionTime;
}
