package lk.ac.uwu.cvi.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PatientResponseDTO(Long id, String characterId, String firstName, String lastName, Integer age,
                                 String address, String motherName, Integer motherAge, String fatherName,
                                 Integer fatherAge, LocalDateTime registeredDateTime,
                                 List<DiagnosisResponseDTO> diagnoses) {
}
