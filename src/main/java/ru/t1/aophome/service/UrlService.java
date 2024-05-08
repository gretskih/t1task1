package ru.t1.aophome.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.t1.aophome.annotation.TrackAsyncTime;
import ru.t1.aophome.annotation.TrackTime;
import ru.t1.aophome.dto.RegUrlDto;
import ru.t1.aophome.dto.RespDto;
import ru.t1.aophome.exception.ServiceException;
import ru.t1.aophome.model.Url;
import ru.t1.aophome.repository.UrlRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UrlService {

    private final UrlRepository repository;
    private final ModelMapper modelMapper;
    public static String ERROR_DB = "Ошибка при выполнении запроса к базе данных.";

    /**
     * Сгенерировать новый код
     *
     * @param regDto
     * @return
     */
    @TrackAsyncTime
    public Optional<RespDto> addUrl(RegUrlDto regDto) throws ServiceException {
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
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
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
    public RespDto getUrlByCode(String code) throws ServiceException {
        Optional<Url> urlFind;
        try {
            urlFind = repository.findByCode(code);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if (urlFind.isEmpty()) {
            throw new ServiceException("Not Found", new IllegalArgumentException("URL для кода " + code + " не найден"));
        }
        return modelMapper.map(urlFind.get(), RespDto.class);
    }

    /**
     * Получить все записи
     *
     * @return
     */
    @TrackTime
    public List<RespDto> getAll() throws ServiceException {
        List<Url> listAll;
        try {
            listAll = repository.findAll();
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if (listAll.isEmpty()) {
            throw new ServiceException("Not Found", new IllegalArgumentException("В базе данных отсутствуют записи"));
        }
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
    public boolean deleteByCode(String code) throws ServiceException {
        try {
            return repository.deleteByCode(code) > 0;
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
    }

    /**
     * Удалить все записи
     */
    @TrackAsyncTime
    public void deleteAll() {
        repository.deleteAll();
    }
}
