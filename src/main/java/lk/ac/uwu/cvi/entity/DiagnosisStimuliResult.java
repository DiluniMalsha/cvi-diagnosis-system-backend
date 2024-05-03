package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DiagnosisStimuliResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer frameFrame;
    private Integer stimuliFrameNumber;
    private Integer resultFrameNumber;
    @ManyToOne
    private DiagnosisStimuli diagnosisStimuli;
}
