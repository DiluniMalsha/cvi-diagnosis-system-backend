package lk.ac.uwu.cvi.dto.response;

public record PendingDiagnosisCheckResponseDTO(long diagnosisId, double COLOR_PREFERENCE, double ATTENTION_TO_LIGHT,
                                               double ATTENTION_TO_MOVEMENT, double VISUAL_LATENCY,
                                               double PREFERRED_VISUAL_FIELD, double VISUAL_COMPLEXITY,
                                               double DIFFICULTY_IN_DISTANCE_VIEWING, double ATYPICAL_VISUAL_REFLEXES,
                                               double DIFFICULTY_IN_VISUAL_NOVELTY,
                                               double ABSENCE_OF_VISUAL_GUIDED_REACH) {
}
