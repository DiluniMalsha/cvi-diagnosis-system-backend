package lk.ac.uwu.cvi.dto.response;

public record DiagnoseStimuliConductResponseDTO(Boolean exists,
                                                Long characteristic,
                                                String file,
                                                String screenSize,
                                                Long child) {
}
