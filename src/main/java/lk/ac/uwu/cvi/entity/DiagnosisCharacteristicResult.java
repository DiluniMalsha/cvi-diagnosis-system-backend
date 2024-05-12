package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DiagnosisCharacteristicResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer frameFrame;
    private Integer resourceFrameNumber;
    private Integer resultFrameNumber;
    @ManyToOne
    private DiagnosisCharacteristic diagnosisCharacteristic;
}
