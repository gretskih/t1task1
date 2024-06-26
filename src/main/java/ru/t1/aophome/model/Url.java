package ru.t1.aophome.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "url")
@Table(indexes = @Index(columnList = "code"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Url {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String code;
}
