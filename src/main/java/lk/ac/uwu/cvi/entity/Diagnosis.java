package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lk.ac.uwu.cvi.enums.ResultPhase;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double score;
    @Enumerated(EnumType.STRING)
    private ResultPhase phase;
    @Enumerated(EnumType.STRING)
    private DiagnosisStatus status;
    @ManyToOne
    private Patient patient;
    @CreationTimestamp
    private LocalDateTime createdDateTime;
}
