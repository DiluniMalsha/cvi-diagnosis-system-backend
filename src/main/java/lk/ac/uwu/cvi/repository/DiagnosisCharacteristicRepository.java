package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Diagnosis;
import lk.ac.uwu.cvi.entity.DiagnosisCharacteristic;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisCharacteristicRepository extends JpaRepository<DiagnosisCharacteristic, Long> {

    List<DiagnosisCharacteristic> findAllByDiagnosis_Id(Long diagnosisId);

    boolean existsByEndDateTimeIsNullAndDiagnosisAndIdIsNot(Diagnosis diagnosis, Long diagnosisCharacteristicId);

    DiagnosisCharacteristic findTopByStatusOrderByStartDateTimeAsc(DiagnosisStatus status);
}
