package lk.ac.uwu.cvi.dto.response;

import lk.ac.uwu.cvi.enums.ResultPhase;

public record DiagnoseResultResponseDTO(Double score, ResultPhase phase) {
}
