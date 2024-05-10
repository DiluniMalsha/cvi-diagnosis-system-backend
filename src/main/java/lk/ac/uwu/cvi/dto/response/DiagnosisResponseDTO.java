package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lk.ac.uwu.cvi.enums.ResultPhase;

import java.time.LocalDateTime;
import java.util.List;

public record DiagnosisResponseDTO(Long id,
                                   Long patientId,
                                   String patientRegistrationId,
                                   String patientName,
                                   Integer patientAge,
                                   LocalDateTime createdDateTime,
                                   LocalDateTime startDateTime,
                                   LocalDateTime endDateTime,
                                   Double score,
                                   ResultPhase phase,
                                   DiagnosisStatus status,
                                   List<DiagnosisStimuliResponseDTO> stimulus) {
}
