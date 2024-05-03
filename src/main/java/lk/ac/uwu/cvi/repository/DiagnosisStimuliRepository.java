package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.DiagnosisStimuli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisStimuliRepository extends JpaRepository<DiagnosisStimuli, Long> {
}
