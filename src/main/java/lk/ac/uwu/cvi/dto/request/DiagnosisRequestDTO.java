package lk.ac.uwu.cvi.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DiagnosisRequestDTO {
    private Long id;
    private List<DiagnosisStimuliRequestDTO> stimulus;
}
