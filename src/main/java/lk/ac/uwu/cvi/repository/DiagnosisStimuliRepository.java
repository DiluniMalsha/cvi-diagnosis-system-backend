package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Diagnosis;
import lk.ac.uwu.cvi.entity.DiagnosisStimuli;
import lk.ac.uwu.cvi.enums.DiagnosisStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisStimuliRepository extends JpaRepository<DiagnosisStimuli, Long> {

    List<DiagnosisStimuli> findAllByDiagnosis_Id(Long diagnosisId);

    boolean existsByEndDateTimeIsNullAndDiagnosisAndIdIsNot(Diagnosis diagnosis, Long diagnoseStimuliId);

    DiagnosisStimuli findTopByStatusOrderByStartDateTimeAsc(DiagnosisStatus status);
}
