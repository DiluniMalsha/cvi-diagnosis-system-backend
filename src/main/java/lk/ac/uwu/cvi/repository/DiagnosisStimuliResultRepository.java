package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.DiagnosisStimuliResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisStimuliResultRepository extends JpaRepository<DiagnosisStimuliResult, Long> {
}
