package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.Characteristic;

public record ResourceResponseDTO(Long id, Characteristic characteristic, Integer totalTime, String resourceName,
                                  String resourceUrl) {
}