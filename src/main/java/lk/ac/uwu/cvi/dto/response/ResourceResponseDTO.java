package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.Characteristic;

import java.util.List;

public record ResourceResponseDTO(Long id, Characteristic characteristic, Integer totalTime, String resource,
                                  List<ResourceFrameResponseDTO> frames) {
}
