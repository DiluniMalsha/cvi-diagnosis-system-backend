package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.Characteristic;

import java.util.List;

public record StimuliResponseDTO(Long id, Characteristic characteristic, Integer totalTime, String resource,
                                 List<StimuliFrameResponseDTO> frames) {
}
