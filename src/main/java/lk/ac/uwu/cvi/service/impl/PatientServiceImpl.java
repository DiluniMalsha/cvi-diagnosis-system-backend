package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.PatientRequestDTO;
import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnosisResponseDTO;
import lk.ac.uwu.cvi.dto.response.PatientResponseDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.entity.Patient;
import lk.ac.uwu.cvi.repository.PatientRepository;
import lk.ac.uwu.cvi.service.DiagnosisService;
import lk.ac.uwu.cvi.service.PatientService;
import lk.ac.uwu.cvi.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientSpecification patientSpecification;

    private final DiagnosisService diagnosisService;

    @Override
    public ResponseDTO createUpdate(RequestDTO request) {
        PatientRequestDTO patientRequest = (PatientRequestDTO) request;
        Patient patient = (patientRequest.getId() == null || patientRequest.getId() == 0) ? new Patient()
                : patientRepository.findById(patientRequest.getId()).orElseThrow(() -> generateNotFoundException("Patient"));
        BeanUtils.copyProperties(patientRequest, patient);
        patient = patientRepository.save(patient);
        return getSuccessResponse("Patient details added to the DB!", getPatientResponseFromEntity(patient));
    }

    @Override
    public ResponseDTO getById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> generateNotFoundException("Patient"));
        return getSuccessResponse(null, getPatientResponseFromEntity(patient));
    }

    @Override
    public ResponseDTO search(RequestDTO request, Pageable pageable) {
        Specification<Patient> spec = patientSpecification.getSearchCriteria((PatientRequestDTO) request);
        return this.getSuccessResponse(null, patientRepository.findAll(spec, pageable).map(this::getPatientResponseFromEntity));
    }

    private PatientResponseDTO getPatientResponseFromEntity(Patient patient) {
        List<DiagnosisResponseDTO> diagnoses = diagnosisService.getDiagnosesForPatient(patient.getId());
        return new PatientResponseDTO(
                patient.getId(),
                patient.getRegistrationId(),
                patient.getRegisteredDateTime(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getAge(),
                patient.getAddress(),
                patient.getGestationAtDelivery(),
                patient.getBirthWeight(),
                patient.getMotherName(),
                patient.getMotherAge(),
                patient.getFatherName(),
                patient.getFatherAge(),
                patient.getOtherComorbidity(),
                diagnoses == null || diagnoses.isEmpty() ? null : diagnoses.get(0).startDateTime(),
                diagnoses == null ? 0 : diagnoses.size(),
                diagnoses == null || diagnoses.isEmpty() ? null : diagnoses.get(0).score(),
                diagnoses == null || diagnoses.isEmpty() ? null : diagnoses.get(0).phase(),
                diagnoses
        );
    }

    @Override
    public ResponseDTO getNextRegistrationId() {
        Patient patient = patientRepository.findTopByOrderByIdDesc();
        return this.getSuccessResponse("Registration Id Generated Successfully!",
                patient == null ? "P001" : "P00" + (Integer.parseInt(patient.getRegistrationId().substring(3)) + 1));
    }
}
