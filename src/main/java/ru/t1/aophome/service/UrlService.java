package ru.t1.aophome.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.t1.aophome.dto.RegUrlDto;
import ru.t1.aophome.dto.ReqCodeDto;
import ru.t1.aophome.dto.RespDto;
import ru.t1.aophome.model.Url;
import ru.t1.aophome.repository.UrlRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Сгенерировать новый код
     *
     * @param regDto
     * @return
     */
    public Optional<RespDto> addUrl(RegUrlDto regDto) {
        String code = RandomStringUtils.randomAlphanumeric(10);
        Url url = Url.builder()
                .url(regDto.url())
                .code(code)
                .build();
        try {
            Url urlNew = repository.save(url);
            return Optional.of(new RespDto(urlNew.getUrl(), urlNew.getCode()));
        } catch (DataIntegrityViolationException e) {
            var urlFind = repository.findByUrl(regDto.url());
            if (urlFind.isPresent()) {
                return Optional.of(new RespDto(urlFind.get().getUrl(), urlFind.get().getCode()));
            }
        }
        return Optional.empty();
    }

    /**
     * Поиск Url по коду
     *
     * @param code
     * @return
     */
    public Optional<RespDto> getUrlByCode(String code) {
        var urlFind = repository.findByCode(code);
        return urlFind.map(url -> new RespDto(url.getUrl(), url.getCode()));
    }

    /**
     * Получить все записи
     *
     * @return
     */
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
    public boolean deleteByCode(String code) {
        return repository.deleteByCode(code) > 0;
    }

    /**
     * Удалить все записи
     */
    public void deleteAll() {
        repository.deleteAll();
    }

}
