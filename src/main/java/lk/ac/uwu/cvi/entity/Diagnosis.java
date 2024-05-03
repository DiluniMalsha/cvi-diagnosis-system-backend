package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lk.ac.uwu.cvi.enums.DiagnosisResult;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lombok.Data;

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
    private DiagnosisResult result;
    @Enumerated(EnumType.STRING)
    private DiagnosisStatus status;
    @ManyToOne
    private Patient patient;
}
