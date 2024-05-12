package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnoseResultResponseDTO;
import lk.ac.uwu.cvi.entity.DiagnosisCharacteristic;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.repository.DiagnosisCharacteristicRepository;
import lk.ac.uwu.cvi.service.ScoreCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreCalculationServiceImpl implements ScoreCalculationService {

    private final DiagnosisCharacteristicRepository diagnosisCharacteristicRepository;

    @Override
    public DiagnoseResultResponseDTO calculateCharacteristicResult(DiagnosisCharacteristicResultRequestDTO request) {
        System.out.println("calculation service - " + request);
        Long diagnosisCharacteristicId = request.getDiagnosisCharacteristicId();
        Optional<DiagnosisCharacteristic> optionalDiagnosisCharacteristic = diagnosisCharacteristicRepository.findById(diagnosisCharacteristicId);
        if (optionalDiagnosisCharacteristic.isPresent()) {
            DiagnosisCharacteristic diagnosisCharacteristic = optionalDiagnosisCharacteristic.get();
            Characteristic characteristic = diagnosisCharacteristic.getCharacteristic();
            switch (characteristic) {
                case COLOR_PREFERENCE -> colorPreference(request.getResult(), diagnosisCharacteristic);
            }
        }
        return new DiagnoseResultResponseDTO(null, null);
    }

    @Override
    public DiagnoseResultResponseDTO calculateDiagnosisResult(Long diagnoseId) {
        return new DiagnoseResultResponseDTO(null, null);
    }

    private void colorPreference(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {
        List<String> looking = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

    }
}
