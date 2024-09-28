package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiagnosisCharacteristicResultRequestDTO extends RequestDTO {
    private Integer characteristic;
    private Long diagnosisCharacteristicId;
    private Double result;

    @Data
    public static class Result {
        private String looking;
        private String time;
    }
}
