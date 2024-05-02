package ru.t1.aophome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.t1.aophome.dto.AvgDto;
import ru.t1.aophome.model.Track;

import java.util.List;

public interface TrackTimeRepository extends JpaRepository<Track, Long> {

    List<Track> findTrackByNameMethod(String nameMethod);

    @Query("""
            SELECT AVG(t.executionTime) 
            FROM track AS t 
            WHERE t.nameMethod = :nameMethod
            """)
    Double findAvgExecutionTimeMethod(@Param("nameMethod") String nameMethod);

    @Query("""
            SELECT AVG(t.executionTime) 
            FROM track AS t 
            WHERE t.nameMethod LIKE :prefixMethod%
            """)
    Double findAvgExecutionTimeGroupMethod(@Param("prefixMethod") String prefixMethod);

    @Query("""
            SELECT SUM(t.executionTime) 
            FROM track AS t 
            WHERE t.nameMethod = :nameMethod
            """)
    Long findSumOfExecutionTimeMethod(@Param("nameMethod") String nameMethod);

    @Query("""
            SELECT COUNT(*) 
            FROM track AS t 
            WHERE t.nameMethod = :nameMethod
            """)
    Long findCountOfExecutionTimeMethod(@Param("nameMethod") String nameMethod);
}
