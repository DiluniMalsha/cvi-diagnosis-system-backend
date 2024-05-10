package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisStimuliResultRequestDTO extends RequestDTO {
    private Integer characteristic;
    private Long diagnosisStimuliId;
    private List result;
}
