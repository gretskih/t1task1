package ru.t1.aophome.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Сущность среднего времени")
public class AvgDto {
    private String nameMethod;
    private Double avgExecutionTime;
}
