package lk.ac.uwu.cvi.dto.response;

public record DiagnoseCharacteristicConductResponseDTO(Boolean exists,
                                                       Long characteristic,
                                                       String file,
                                                       String screenSize,
                                                       Long diagnosisCharacteristicId) {
}
