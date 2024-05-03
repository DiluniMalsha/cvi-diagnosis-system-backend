package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
