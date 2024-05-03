package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.DiagnosisResult;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;

import java.time.LocalDateTime;
import java.util.List;

public record DiagnosisResponseDTO(Long id, Long patientId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                   Double score, DiagnosisResult result, DiagnosisStatus status,
                                   List<DiagnosisStimuliResponseDTO> stimulus) {
}
