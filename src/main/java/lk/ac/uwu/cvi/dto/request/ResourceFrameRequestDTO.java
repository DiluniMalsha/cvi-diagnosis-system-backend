package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceFrameRequestDTO extends RequestDTO {
    private Long id;
    private Integer time;
    private Integer frameNumber;
}
