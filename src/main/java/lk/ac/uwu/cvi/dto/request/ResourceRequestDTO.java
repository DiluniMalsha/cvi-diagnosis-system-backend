package lk.ac.uwu.cvi.dto.request;

import lk.ac.uwu.cvi.enums.Characteristic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceRequestDTO extends RequestDTO {
    private Long id;
    private Characteristic characteristic;
    private Integer totalTime;
    private String resource;
    private List<ResourceFrameRequestDTO> frames;
}
