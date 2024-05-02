package ru.t1.aophome.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.aophome.dto.RegUrlDto;
import ru.t1.aophome.dto.ReqCodeDto;
import ru.t1.aophome.dto.RespDto;
import ru.t1.aophome.service.UrlService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService service;

    @PostMapping("/code")
    public ResponseEntity<RespDto> code(@RequestBody RegUrlDto regDto) {
        return service.addUrl(regDto)
                .map(c -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(c))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)::build);
    }

    @GetMapping("/{code}")
    public ResponseEntity<RespDto> url(@PathVariable String code) {
        return service.getUrlByCode(code)
                .map(u -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(u))
                .orElseGet(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)::build);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RespDto>> all() {
        var listAll = service.getAll();
        if(!listAll.isEmpty()) {
            return new ResponseEntity<>(listAll, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {
        if(service.deleteByCode(code)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


























