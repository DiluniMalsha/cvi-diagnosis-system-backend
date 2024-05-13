package lk.ac.uwu.cvi.dto.response;

public record DiagnoseCharacteristicConductResponseDTO(Boolean exists,
                                                       Long characteristic,
                                                       String resource,
                                                       String screenSize,
                                                       Long diagnosisCharacteristicId) {
}
