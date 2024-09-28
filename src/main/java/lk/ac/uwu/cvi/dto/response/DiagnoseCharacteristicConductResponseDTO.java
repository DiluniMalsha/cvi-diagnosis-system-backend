package lk.ac.uwu.cvi.dto.response;

public record DiagnoseCharacteristicConductResponseDTO(Boolean exists,
                                                       Long characteristic,
                                                       Long diagnosisCharacteristicId) {
}
