package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.DiagnosisStimuliResultRequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnoseResultResponseDTO;
import lk.ac.uwu.cvi.service.ScoreCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreCalculationServiceImpl implements ScoreCalculationService {

    @Override
    public DiagnoseResultResponseDTO calculateCharacteristicResult(DiagnosisStimuliResultRequestDTO request) {
        return new DiagnoseResultResponseDTO(null, null);
    }

    @Override
    public DiagnoseResultResponseDTO calculateDiagnosisResult(Long diagnoseId) {
        return new DiagnoseResultResponseDTO(null, null);
    }
}
