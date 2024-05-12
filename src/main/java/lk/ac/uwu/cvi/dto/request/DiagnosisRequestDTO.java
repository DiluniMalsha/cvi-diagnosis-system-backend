package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisRequestDTO extends RequestDTO {
    private Long id;
    private Long patientId;
    private List<DiagnosisCharacteristicRequestDTO> characteristicDetails;
}
