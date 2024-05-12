package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;

import java.time.LocalDateTime;

public record DiagnosisCharacteristicResponseDTO(Long id,
                                                 Long resourceId,
                                                 String resourceName,
                                                 DiagnosisStatus status,
                                                 Characteristic characteristic,
                                                 Double score,
                                                 LocalDateTime startDateTime,
                                                 LocalDateTime endDateTime) {
}
