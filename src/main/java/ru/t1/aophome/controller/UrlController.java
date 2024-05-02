package ru.t1.aophome.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.aophome.dto.RegUrlDto;
import ru.t1.aophome.dto.RespDto;
import ru.t1.aophome.service.UrlService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Tag(
        name="Контроллер ссылок",
        description="Предоставление фукционала по работе со ссылками"
)
@RestController
@AllArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService service;
    private final ExecutorService executorService;

    @Operation(
            summary = "Регистрация ссылки",
            description = "Позволяет зарегистрировать ссылку и получить ассоциированный код"
    )
    @PostMapping("/code")
    public ResponseEntity<RespDto> code(@RequestBody RegUrlDto regDto) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> service.addUrl(regDto)).get()
                .map(c -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(c))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)::build);
    }

    @Operation(
            summary = "Переадресация",
            description = "Позволяет по ассоциированному коду получить ссылку."
    )
    @GetMapping("/{code}")
    public ResponseEntity<RespDto> url(@PathVariable String code) {
        return service.getUrlByCode(code)
                .map(u -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(u))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @Operation(
            summary = "Список",
            description = "Получение всего списка код-ссылка."
    )
    @GetMapping("/all")
    public ResponseEntity<List<RespDto>> all() {
        var listAll = service.getAll();
        if(!listAll.isEmpty()) {
            return new ResponseEntity<>(listAll, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Удаление ссылки",
            description = "Удаление ссылки по ассоциированному коду из сервиса."
    )
    @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {
        if(service.deleteByCode(code)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Удаление ссылок",
            description = "Удаление всех ссылок по ассоциированному коду из сервиса."
    )
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


























