package lk.ac.uwu.cvi.dto.request;

import lk.ac.uwu.cvi.enums.Characteristic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisStimuliRequestDTO extends RequestDTO {
    private Long id;
    private Long stimuliId;
    private Characteristic characteristic;
}
