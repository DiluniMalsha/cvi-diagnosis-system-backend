package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lk.ac.uwu.cvi.enums.Characteristic;
import lombok.Data;

@Data
@Entity
public class Stimuli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Characteristic characteristic;
    private Integer totalTime;
    private String resource;
}
