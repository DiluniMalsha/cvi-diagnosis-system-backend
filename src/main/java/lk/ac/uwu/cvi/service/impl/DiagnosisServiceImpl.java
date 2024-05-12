package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.request.DiagnosisRequestDTO;
import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.*;
import lk.ac.uwu.cvi.entity.Diagnosis;
import lk.ac.uwu.cvi.entity.DiagnosisCharacteristic;
import lk.ac.uwu.cvi.entity.Patient;
import lk.ac.uwu.cvi.entity.Resource;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lk.ac.uwu.cvi.repository.DiagnosisCharacteristicRepository;
import lk.ac.uwu.cvi.repository.DiagnosisRepository;
import lk.ac.uwu.cvi.repository.PatientRepository;
import lk.ac.uwu.cvi.repository.ResourceRepository;
import lk.ac.uwu.cvi.service.DiagnosisService;
import lk.ac.uwu.cvi.service.ScoreCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    // REPOSITORIES
    private final PatientRepository patientRepository;
    private final ResourceRepository resourceRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisCharacteristicRepository diagnosisCharacteristicRepository;

    // SERVICES
    private final ScoreCalculationService scoreCalculationService;

    @Override
    @Transactional
    public ResponseDTO createUpdate(RequestDTO request) {

        DiagnosisRequestDTO diagnosisRequest = (DiagnosisRequestDTO) request;
        Patient patient = patientRepository.findById(diagnosisRequest.getPatientId()).orElseThrow(() -> generateNotFoundException("Patient"));

        Diagnosis diagnosis;

        if (diagnosisRequest.getId() != null && diagnosisRequest.getId() != 0) {
            diagnosis = diagnosisRepository.findByIdAndPatient(diagnosisRequest.getId(), patient).orElseThrow(() -> generateNotFoundException("Diagnosis"));
            if (diagnosis.getStatus() == DiagnosisStatus.COMPLETED)
                throw generateCustomServiceException(401, "Diagnosis is completed. Can not update!");
        } else {
            diagnosis = new Diagnosis();
            diagnosis.setPatient(patient);
            diagnosis.setStatus(DiagnosisStatus.PENDING);
            diagnosis = diagnosisRepository.save(diagnosis);
        }

        Diagnosis finalDiagnosis = diagnosis;

        List<DiagnosisCharacteristic> diagnosisCharacteristics = diagnosisRequest.getCharacteristicDetails().stream().map(s -> {
            DiagnosisCharacteristic diagnosisCharacteristic;
            if (s.getId() != null && s.getId() != 0) {
                diagnosisCharacteristic = diagnosisCharacteristicRepository.findById(s.getId()).orElseThrow(() -> generateNotFoundException("Diagnosis Characteristic"));
                if (!diagnosisCharacteristic.getStatus().equals(DiagnosisStatus.PENDING))
                    throw generateCustomServiceException(401, "Can not update diagnose characteristic. It is ongoing or completed!");
            } else {
                diagnosisCharacteristic = new DiagnosisCharacteristic();
                diagnosisCharacteristic.setStatus(DiagnosisStatus.PENDING);
            }
            Resource resource = resourceRepository.findByIdAndCharacteristic(s.getResourceId(), s.getCharacteristic()).orElseThrow(() -> generateNotFoundException("Resource"));
            diagnosisCharacteristic.setDiagnosis(finalDiagnosis);
            diagnosisCharacteristic.setResource(resource);
            diagnosisCharacteristic.setCharacteristic(s.getCharacteristic());
            return diagnosisCharacteristic;
        }).toList();

        diagnosisCharacteristicRepository.saveAll(diagnosisCharacteristics);

        return getSuccessResponse("Diagnosis details added to the DB!", getDiagnosisResponseFromEntity(finalDiagnosis));
    }

    @Override
    public ResponseDTO getById(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElseThrow(() -> generateNotFoundException("Diagnosis"));
        return getSuccessResponse(null, getDiagnosisResponseFromEntity(diagnosis));
    }

    @Override
    public ResponseDTO search(RequestDTO request, Pageable pageable) {
        return null;
    }

    @Override
    public List<DiagnosisResponseDTO> getDiagnosesForPatient(Long patientId) {
        return diagnosisRepository.findAllByPatient_Id(patientId).stream().map(this::getDiagnosisResponseFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseDTO startDiagnosisCharacteristicTest(Long id) {
        DiagnosisCharacteristic diagnosisCharacteristic = diagnosisCharacteristicRepository.findById(id).orElseThrow(() -> generateNotFoundException("Diagnosis Characteristic"));
        diagnosisCharacteristic.setStartDateTime(LocalDateTime.now());
        diagnosisCharacteristic.setStatus(DiagnosisStatus.QUEUED);
        diagnosisCharacteristic = diagnosisCharacteristicRepository.save(diagnosisCharacteristic);

        Diagnosis diagnosis = diagnosisCharacteristic.getDiagnosis();
        if (diagnosis.getStartDateTime() == null) {
            diagnosis.setStartDateTime(LocalDateTime.now());
            diagnosis.setStatus(DiagnosisStatus.IN_PROGRESS);
            diagnosisRepository.save(diagnosis);
        }

        return getSuccessResponse("Diagnosis characteristic test is started!", null);
    }

    @Override
    @Transactional
    public ResponseDTO endDiagnosisCharacteristicTest(RequestDTO request) {
        DiagnosisCharacteristicResultRequestDTO resultRequest = (DiagnosisCharacteristicResultRequestDTO) request;
        DiagnosisCharacteristic diagnosisCharacteristic = diagnosisCharacteristicRepository.findById(resultRequest.getDiagnosisCharacteristicId())
                .orElseThrow(() -> generateNotFoundException("Diagnosis Characteristic"));
        diagnosisCharacteristic.setEndDateTime(LocalDateTime.now());
        diagnosisCharacteristic.setStatus(DiagnosisStatus.COMPLETED);
        DiagnoseResultResponseDTO result = scoreCalculationService.calculateCharacteristicResult(resultRequest);
        diagnosisCharacteristic.setScore(result.score());
        diagnosisCharacteristic = diagnosisCharacteristicRepository.save(diagnosisCharacteristic);
        // TODO MAP RESPONSE DATA

        Diagnosis diagnosis = diagnosisCharacteristic.getDiagnosis();
        if (!diagnosisCharacteristicRepository.existsByEndDateTimeIsNullAndDiagnosisAndIdIsNot(diagnosis, diagnosisCharacteristic.getId())) {
            diagnosis.setEndDateTime(LocalDateTime.now());
            diagnosis.setStatus(DiagnosisStatus.COMPLETED);
            DiagnoseResultResponseDTO diagnoseResult = scoreCalculationService.calculateDiagnosisResult(diagnosis.getId());
            diagnosis.setScore(diagnoseResult.score());
            diagnosis.setPhase(diagnoseResult.phase());
            diagnosisRepository.save(diagnosis);
        }
        return getSuccessResponse("Diagnosis characteristic test is completed!", null);
    }

    @Override
    public ResponseDTO checkDiagnosisConductStatus() {
        DiagnosisCharacteristic currentTest = diagnosisCharacteristicRepository.findTopByStatusOrderByStartDateTimeAsc(DiagnosisStatus.QUEUED);

        DiagnoseCharacteristicConductResponseDTO result = currentTest == null ?
                new DiagnoseCharacteristicConductResponseDTO(false, null, null, null, null)
                : new DiagnoseCharacteristicConductResponseDTO(true, getCharacteristicId(currentTest.getCharacteristic()),
                currentTest.getResource().getResourceName(),
                "", currentTest.getId()
        );

        return new ResponseDTO(
                true,
                "Check is success!",
                result);
    }

    private Long getCharacteristicId(Characteristic characteristic) {
        return switch (characteristic) {
            case COLOR_PREFERENCE -> 1L;
            case ATTENTION_TO_LIGHT -> 2L;
            case ATTENTION_TO_MOVEMENT -> 3L;
            case VISUAL_LATENCY -> 4L;
            case PREFERRED_VISUAL_FIELD -> 5L;
            case VISUAL_COMPLEXITY -> 6L;
            case DIFFICULTY_IN_DISTANCE_VIEWING -> 7L;
            case ATYPICAL_VISUAL_REFLEXES -> 8L;
            case DIFFICULTY_IN_VISUAL_NOVELTY -> 9L;
            case ABSENCE_OF_VISUAL_GUIDED_REACH -> 10L;
        };
    }

    private DiagnosisResponseDTO getDiagnosisResponseFromEntity(Diagnosis diagnosis) {
        Patient patient = diagnosis.getPatient();
        return new DiagnosisResponseDTO(diagnosis.getId(),
                patient.getId(),
                patient.getRegistrationId(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getAge(),
                diagnosis.getCreatedDateTime(),
                diagnosis.getStartDateTime(),
                diagnosis.getEndDateTime(),
                diagnosis.getScore(),
                diagnosis.getPhase(),
                diagnosis.getStatus(),
                getStimulusForDiagnosis(diagnosis));
    }

    private List<DiagnosisCharacteristicResponseDTO> getStimulusForDiagnosis(Diagnosis diagnosis) {
        List<DiagnosisCharacteristic> diagnosisStimulus = diagnosisCharacteristicRepository.findAllByDiagnosis_Id(diagnosis.getId());
        return diagnosisStimulus.stream().map(s -> new DiagnosisCharacteristicResponseDTO(
                s.getId(),
                s.getResource().getId(),
                s.getResource().getResourceName(),
                s.getStatus(),
                s.getCharacteristic(),
                s.getScore(),
                s.getStartDateTime(),
                s.getEndDateTime()
        )).toList();
    }
}
