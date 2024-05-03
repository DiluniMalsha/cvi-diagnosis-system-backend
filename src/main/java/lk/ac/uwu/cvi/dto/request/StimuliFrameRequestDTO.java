package lk.ac.uwu.cvi.dto.request;

import lombok.Data;

@Data
public class StimuliFrameRequestDTO {
    private Long id;
    private Integer time;
    private Integer frameNumber;
}
