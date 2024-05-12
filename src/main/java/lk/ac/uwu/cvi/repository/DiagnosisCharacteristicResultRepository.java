package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.DiagnosisCharacteristicResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisCharacteristicResultRepository extends JpaRepository<DiagnosisCharacteristicResult, Long> {
}
