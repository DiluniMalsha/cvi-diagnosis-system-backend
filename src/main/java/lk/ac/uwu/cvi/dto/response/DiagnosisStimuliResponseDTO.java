package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.DiagnosisStatus;

import java.time.LocalDateTime;
import java.util.List;

public record DiagnosisStimuliResponseDTO(Long id, Long stimuliId, DiagnosisStatus status, Double score,
                                          LocalDateTime startDateTime, LocalDateTime endDateTime,
                                          List<DiagnosisStimuliResultResponseDTO> results) {
}
