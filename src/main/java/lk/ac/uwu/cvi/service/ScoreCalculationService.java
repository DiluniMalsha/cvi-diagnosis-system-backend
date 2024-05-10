package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.request.DiagnosisStimuliResultRequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnoseResultResponseDTO;

public interface ScoreCalculationService {

    DiagnoseResultResponseDTO calculateCharacteristicResult(DiagnosisStimuliResultRequestDTO request);

    DiagnoseResultResponseDTO calculateDiagnosisResult(Long diagnoseId);
}
