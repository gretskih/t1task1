package ru.t1.aophome.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.t1.aophome.annotation.TrackAsyncTime;
import ru.t1.aophome.annotation.TrackTime;
import ru.t1.aophome.dto.RegUrlDto;
import ru.t1.aophome.dto.RespDto;
import ru.t1.aophome.model.Url;
import ru.t1.aophome.repository.UrlRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class UrlService {

    private final UrlRepository repository;
    private final ModelMapper modelMapper;
    private final ExecutorService executorService;

    /**
     * Сгенерировать новый код
     *
     * @param regDto
     * @return
     */
    @TrackAsyncTime
    public Optional<RespDto> addUrl(RegUrlDto regDto) {
        Url url = Url.builder()
                .url(regDto.url())
                .code(generateCode())
                .build();
        try {
            Url urlNew = repository.save(url);
            return Optional.of(modelMapper.map(urlNew, RespDto.class));
        } catch (DataIntegrityViolationException e) {
            var urlFind = repository.findByUrl(regDto.url());
            if (urlFind.isPresent()) {
                return Optional.of(modelMapper.map(urlFind, RespDto.class));
            }
        }
        return Optional.empty();
    }

    private String generateCode() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    /**
     * Поиск Url по коду
     *
     * @param code
     * @return
     */
    @TrackTime
    public Optional<RespDto> getUrlByCode(String code) {
        var urlFind = repository.findByCode(code);
        return urlFind.map(url -> new RespDto(url.getUrl(), url.getCode()));
    }

    /**
     * Получить все записи
     *
     * @return
     */
    @TrackTime
    public List<RespDto> getAll() {
        var listAll = repository.findAll();
        Type listType = new TypeToken<List<RespDto>>() { }.getType();
        return modelMapper.map(listAll, listType);
    }

    /**
     * Удалить по коду
     *
     * @param code
     * @return
     */
    @TrackTime
    public boolean deleteByCode(String code) {
        return repository.deleteByCode(code) > 0;
    }

    /**
     * Удалить все записи
     */
    public void deleteAll() {
        executorService.submit(() -> {
            repository.deleteAll();
        });
    }
}
