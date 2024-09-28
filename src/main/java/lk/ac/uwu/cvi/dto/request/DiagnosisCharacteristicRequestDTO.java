package lk.ac.uwu.cvi.dto.request;

import lk.ac.uwu.cvi.enums.Characteristic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisCharacteristicRequestDTO extends RequestDTO {
    private Long id;
    private Characteristic characteristic;
}
