package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.request.DiagnosisRequestDTO;
import lk.ac.uwu.cvi.dto.request.DiagnosisScoreUpdateRequestDTO;
import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.*;
import lk.ac.uwu.cvi.entity.Diagnosis;
import lk.ac.uwu.cvi.entity.DiagnosisCharacteristic;
import lk.ac.uwu.cvi.entity.Patient;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import lk.ac.uwu.cvi.enums.ResultPhase;
import lk.ac.uwu.cvi.repository.DiagnosisCharacteristicRepository;
import lk.ac.uwu.cvi.repository.DiagnosisRepository;
import lk.ac.uwu.cvi.repository.PatientRepository;
import lk.ac.uwu.cvi.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    // REPOSITORIES
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisCharacteristicRepository diagnosisCharacteristicRepository;

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
            diagnosisCharacteristic.setDiagnosis(finalDiagnosis);
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
        diagnosisCharacteristic.setScore(resultRequest.getResult());
        diagnosisCharacteristic = diagnosisCharacteristicRepository.save(diagnosisCharacteristic);

        Diagnosis diagnosis = diagnosisCharacteristic.getDiagnosis();
        if (!diagnosisCharacteristicRepository.existsByEndDateTimeIsNullAndDiagnosisAndIdIsNot(diagnosis, diagnosisCharacteristic.getId())) {
            diagnosis.setEndDateTime(LocalDateTime.now());
            diagnosis.setStatus(DiagnosisStatus.DIAGNOSTIC_COMPLETE);
            diagnosisRepository.save(diagnosis);
        }

        return getSuccessResponse("Diagnosis characteristic test is completed!", null);
    }

    @Override
    public ResponseDTO startDiagnosisPhasePrediction(Long diagnosisId) {
        Diagnosis diagnosis = diagnosisRepository.findTopByStatusAndPhaseIsNullOrderByEndDateTimeAsc(DiagnosisStatus.FINAL_SCORE_PENDING);
        diagnosis.setStatus(DiagnosisStatus.FINAL_SCORE_PENDING);
        diagnosisRepository.save(diagnosis);
        return getSuccessResponse("Diagnosis Phase Prediction Started!", null);
    }

    @Override
    public ResponseDTO checkPendingDiagnosisPhase() {

        Diagnosis diagnosis = diagnosisRepository.findTopByStatusAndPhaseIsNullOrderByEndDateTimeAsc(DiagnosisStatus.FINAL_SCORE_PENDING);

        List<DiagnosisCharacteristic> characteristics = diagnosisCharacteristicRepository.findAllByDiagnosis_Id(diagnosis.getId());

        double colorPreference = 0,
                attentionToLight = 0,
                attentionToMovement = 0,
                visualLatency = 0,
                preferredVisualField = 0,
                visualComplexity = 0,
                difficulty_in_distance_viewing = 0,
                atypicalVisualReflexes = 0,
                difficultyInVisualNovelty = 0,
                absenceOfVisualGuidedReach = 0;

        for (DiagnosisCharacteristic c : characteristics) {
            switch (c.getCharacteristic()) {
                case COLOR_PREFERENCE -> colorPreference = c.getScore();
                case ATTENTION_TO_LIGHT -> attentionToLight = c.getScore();
                case ATTENTION_TO_MOVEMENT -> attentionToMovement = c.getScore();
                case VISUAL_LATENCY -> visualLatency = c.getScore();
                case PREFERRED_VISUAL_FIELD -> preferredVisualField = c.getScore();
                case VISUAL_COMPLEXITY -> visualComplexity = c.getScore();
                case DIFFICULTY_IN_DISTANCE_VIEWING -> difficulty_in_distance_viewing = c.getScore();
                case ATYPICAL_VISUAL_REFLEXES -> atypicalVisualReflexes = c.getScore();
                case DIFFICULTY_IN_VISUAL_NOVELTY -> difficultyInVisualNovelty = c.getScore();
                case ABSENCE_OF_VISUAL_GUIDED_REACH -> absenceOfVisualGuidedReach = c.getScore();
            }
        }

        return getSuccessResponse(null, new PendingDiagnosisCheckResponseDTO(diagnosis.getId(),
                colorPreference,
                attentionToLight,
                attentionToMovement,
                visualLatency,
                preferredVisualField,
                visualComplexity,
                difficulty_in_distance_viewing,
                atypicalVisualReflexes,
                difficultyInVisualNovelty,
                absenceOfVisualGuidedReach
        ));
    }

    @Override
    public ResponseDTO updateDiagnosisPhase(DiagnosisScoreUpdateRequestDTO request) {
        Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findById(request.getDiagnosisId());
        if (optionalDiagnosis.isPresent()) {
            Diagnosis diagnosis = optionalDiagnosis.get();
            diagnosis.setPhase(getPhaseById(request.getPhase()));
            diagnosis.setStatus(DiagnosisStatus.COMPLETED);
            diagnosisRepository.save(diagnosis);
        }
        return getSuccessResponse("Diagnosis result updated!", null);
    }

    private ResultPhase getPhaseById(Integer phase) {
        return switch (phase) {
            case 1 -> ResultPhase.PHASE_I;
            case 2 -> ResultPhase.PHASE_II;
            case 3 -> ResultPhase.PHASE_III;
            default -> null;
        };
    }

    @Override
    public ResponseDTO checkDiagnosisConductStatus() {
        DiagnosisCharacteristic currentTest = diagnosisCharacteristicRepository.findTopByStatusOrderByStartDateTimeAsc(DiagnosisStatus.QUEUED);

        DiagnoseCharacteristicConductResponseDTO result = currentTest == null ?
                new DiagnoseCharacteristicConductResponseDTO(false, null, null)
                : new DiagnoseCharacteristicConductResponseDTO(true, getCharacteristicId(currentTest.getCharacteristic()),
                currentTest.getId()
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
                s.getStatus(),
                s.getCharacteristic(),
                s.getScore(),
                s.getStartDateTime(),
                s.getEndDateTime()
        )).toList();
    }
}
