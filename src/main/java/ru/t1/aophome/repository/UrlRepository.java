package ru.t1.aophome.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.aophome.model.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByUrl(String url);
    Optional<Url> findByCode(String code);
    @Transactional
    int deleteByCode(String code);
    void deleteAll();
    Url save(Url url);
}
