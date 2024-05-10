package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class DiagnosisStimuli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DiagnosisStatus status;
    @Enumerated(EnumType.STRING)
    private Characteristic characteristic;
    private Double score;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    private Diagnosis diagnosis;
    @ManyToOne
    private Stimuli stimuli;
}
