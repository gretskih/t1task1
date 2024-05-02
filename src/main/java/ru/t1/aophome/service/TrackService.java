package ru.t1.aophome.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ru.t1.aophome.dto.AvgDto;
import ru.t1.aophome.dto.TrackDto;
import ru.t1.aophome.model.Track;
import ru.t1.aophome.repository.TrackTimeRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

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
    public Optional<List<TrackDto>> findTrackByMethodName(String name) {
        var listTrack = repository.findTrackByNameMethod(name);
        Type listType = new TypeToken<List<TrackDto>>() { }.getType();
        return Optional.of(modelMapper.map(listTrack, listType));
    }

    /**
     * Среднее время выподлнения метода
     *
     * @param name
     * @return
     */
    public Optional<AvgDto> getAvgTimeMethod(String name) {
        return Optional.of(new AvgDto(name, repository.findAvgExecutionTimeMethod(name)));
    }

    /**
     * Среднее время выполнения группы методов
     *
     * @param likeName
     * @return
     */
    public Optional<AvgDto> getAvgTimeGroupMethod(String likeName) {
        return Optional.of(new AvgDto(likeName + "*", repository.findAvgExecutionTimeGroupMethod(likeName)));
    }

    /**
     * Суммарное время выполнения метода
     *
     * @param name
     * @return
     */
    public Optional<Long> getSumTimeMethod(String name) {
        return Optional.ofNullable(repository.findSumOfExecutionTimeMethod(name));
    }

    /**
     * Число вызовов метода
     *
     * @param name
     * @return
     */
    public Optional<Long> getCountExecutionMethod(String name) {
        return Optional.ofNullable(repository.findCountOfExecutionTimeMethod(name));
    }
}
