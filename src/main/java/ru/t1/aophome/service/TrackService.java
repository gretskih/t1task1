package ru.t1.aophome.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ru.t1.aophome.dto.AvgDto;
import ru.t1.aophome.dto.TrackDto;
import ru.t1.aophome.exception.ServiceException;
import ru.t1.aophome.model.Track;
import ru.t1.aophome.repository.TrackTimeRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static ru.t1.aophome.service.UrlService.ERROR_DB;

@Service
@AllArgsConstructor
public class TrackService {
    private final TrackTimeRepository repository;
    private final ModelMapper modelMapper;

    public void save(Track track) {
        repository.save(track);
    }
    /**
     * Список записей по имени метода
     *
     * @param name
     * @return
     */
    public List<TrackDto> findTrackByMethodName(String name) throws ServiceException {
        List<Track> listTrack;
        try {
            listTrack = repository.findTrackByNameMethod(name);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if(listTrack.isEmpty()) {
            throw new ServiceException("Not Found", new IllegalArgumentException("Записи в базе данных для метода: " + name + " не найдены"));
        }
        Type listType = new TypeToken<List<TrackDto>>() { }.getType();
        return modelMapper.map(listTrack, listType);
    }


    /**
     * Среднее время выполнения метода
     *
     * @param name
     * @return
     */
    public AvgDto getAvgTimeMethod(String name) throws ServiceException {
        Optional<Double> result;
        try {
            result = repository.findAvgExecutionTimeMethod(name);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if(result.isEmpty()) {
            throw new ServiceException("Not Found",
                    new IllegalArgumentException("Записи в базе данных для метода: " + name + " не найдены"));
        }
        return new AvgDto(name, result.get());
    }

    /**
     * Среднее время выполнения группы методов
     *
     * @param likeName
     * @return
     */
    public AvgDto getAvgTimeGroupMethod(String likeName) throws ServiceException {
        Optional<Double> result;
        try {
            result = repository.findAvgExecutionTimeGroupMethod(likeName);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if(result.isEmpty()) {
            throw new ServiceException("Not Found",
                    new IllegalArgumentException("Записи в базе данных для метода: " + likeName + " не найдены"));
        }
        return new AvgDto(likeName, result.get());
    }

    /**
     * Суммарное время выполнения метода
     *
     * @param name
     * @return
     */
    public Long getSumTimeMethod(String name) throws ServiceException {
        Optional<Long> result;
        try {
            result = repository.findSumOfExecutionTimeMethod(name);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if(result.isEmpty()) {
            throw new ServiceException("Not Found",
                    new IllegalArgumentException("Записи в базе данных для метода: " + name + " не найдены"));
        }
        return result.get();
    }

    /**
     * Число вызовов метода
     *
     * @param name
     * @return
     */
    public Long getCountExecutionMethod(String name) throws ServiceException {
        Long result;
        try {
            result = repository.findCountOfExecutionTimeMethod(name);
        } catch (Exception e) {
            throw new ServiceException(ERROR_DB, e);
        }
        if(result == 0) {
            throw new ServiceException("Not Found",
                    new IllegalArgumentException("Записи в базе данных для метода: " + name + " не найдены"));
        }
        return result;
    }
}
