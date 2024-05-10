package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Diagnosis;
import lk.ac.uwu.cvi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    List<Diagnosis> findAllByPatient_Id(Long patientId);

    Optional<Diagnosis> findByIdAndPatient(Long id, Patient patient);
}
