package lk.ac.uwu.cvi.dto.request;

import lk.ac.uwu.cvi.enums.Characteristic;
import lombok.Data;

import java.util.List;

@Data
public class StimuliRequestDTO {
    private Long id;
    private Characteristic characteristic;
    private Integer totalTime;
    private String resource;
    private List<StimuliFrameRequestDTO> frames;
}
