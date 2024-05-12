package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnosisResponseDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface DiagnosisService extends CommonService {
    List<DiagnosisResponseDTO> getDiagnosesForPatient(Long patientId);

    @Transactional
    ResponseDTO startDiagnosisCharacteristicTest(Long id);

    @Transactional
    ResponseDTO endDiagnosisCharacteristicTest(RequestDTO request);

    ResponseDTO checkDiagnosisConductStatus();

}
