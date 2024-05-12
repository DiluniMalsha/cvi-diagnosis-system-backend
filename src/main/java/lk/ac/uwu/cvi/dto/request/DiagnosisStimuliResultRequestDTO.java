package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisStimuliResultRequestDTO extends RequestDTO {
    private Integer characteristic;
    private Long diagnosisStimuliId;
    private Object result;
}
