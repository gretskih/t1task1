package ru.t1.aophome.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.aophome.dto.*;
import ru.t1.aophome.service.TrackService;

import java.util.List;

@Tag(
        name="Контроллер статистики",
        description="Предоставление статистической информации по работе сервиса"
)
@RestController
@AllArgsConstructor
@RequestMapping("/time")
public class TrackTimeController {
    private final TrackService service;

    @Operation(
            summary = "Время выполнения",
            description = "Список вызовов метода со временем выполнения в мс"
    )
    @PostMapping("/method")
    public ResponseEntity<List<TrackDto>> findTimeByNameMethod(@RequestBody MethodNameDto method) {
        return service.findTrackByMethodName(method.name())
                .map(t -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(t))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @Operation(
            summary = "Среднее время",
            description = "Среднее время выполнения метода в мс"
    )
    @PostMapping("/avg")
    public ResponseEntity<AvgDto> avgTimeMethod(@RequestBody MethodNameDto method) {
        return service.getAvgTimeMethod(method.name())
                .map(t -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(t))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @Operation(
            summary = "Среднее время для группы методов",
            description = "Среднее время выполнения группы метода в мс"
    )
    @PostMapping("/avg/group")
    public ResponseEntity<AvgDto> avgTimeGroupMethod(@RequestBody MethodNameDto likeMethod) {
        return service.getAvgTimeGroupMethod(likeMethod.name())
                .map(t -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(t))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @Operation(
            summary = "Суммарное время",
            description = "Суммарное время выполнения метода в мс"
    )
    @PostMapping("/sum")
    public ResponseEntity<SumDto> sumTimeMethod(@RequestBody MethodNameDto method) {
        return service.getSumTimeMethod(method.name())
                .map(t -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new SumDto(method.name(), t)))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @Operation(
            summary = "Число вызовов",
            description = "Число вызовов метода"
    )
    @PostMapping("/count")
    public ResponseEntity<CountDto> countExecutingMethod(@RequestBody MethodNameDto method) {
        return service.getCountExecutionMethod(method.name())
                .map(t -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new CountDto(method.name(), t)))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }
}
