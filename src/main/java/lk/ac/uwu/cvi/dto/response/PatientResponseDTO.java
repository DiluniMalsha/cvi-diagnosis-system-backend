package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.ResultPhase;

import java.time.LocalDateTime;
import java.util.List;

public record PatientResponseDTO(Long id,
                                 String registrationId,
                                 LocalDateTime registeredDateTime,
                                 String firstName,
                                 String lastName,
                                 LocalDateTime dateOfBirth,
                                 Integer age,
                                 String address,
                                 Integer gestationAtDelivery,
                                 Double birthWeight,
                                 String motherName,
                                 Integer motherAge,
                                 String fatherName,
                                 Integer fatherAge,
                                 String otherComorbidity,
                                 LocalDateTime latestDiagnosisDate,
                                 Integer numberOdDiagnoses,
                                 Double latestScore,
                                 ResultPhase latestPhase,
                                 List<DiagnosisResponseDTO> diagnoses) {
}
