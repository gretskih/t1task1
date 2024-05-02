package ru.t1.aophome.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "track")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Track {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_method")
    private String nameMethod;
    @Column(name = "execution_time")
    private Long executionTime;
}
