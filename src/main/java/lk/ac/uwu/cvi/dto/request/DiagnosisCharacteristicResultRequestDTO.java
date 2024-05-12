package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisCharacteristicResultRequestDTO extends RequestDTO {
    private Integer characteristic;
    private Long diagnosisCharacteristicId;
    private List<Result> result;

    @Data
    public static class Result {
        private String looking;
        private String time;
    }
}
