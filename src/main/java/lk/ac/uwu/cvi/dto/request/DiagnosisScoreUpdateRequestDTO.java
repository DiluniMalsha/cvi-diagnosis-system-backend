package lk.ac.uwu.cvi.dto.request;

import lombok.Data;

@Data
public class DiagnosisScoreUpdateRequestDTO {
    private Long diagnosisId;
    private Integer phase;
}