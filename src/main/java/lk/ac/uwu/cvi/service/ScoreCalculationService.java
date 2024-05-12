package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnoseResultResponseDTO;

public interface ScoreCalculationService {

    DiagnoseResultResponseDTO calculateCharacteristicResult(DiagnosisCharacteristicResultRequestDTO request);

    DiagnoseResultResponseDTO calculateDiagnosisResult(Long diagnoseId);
}
