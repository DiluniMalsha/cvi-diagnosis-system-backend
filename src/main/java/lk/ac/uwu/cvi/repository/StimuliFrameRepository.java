package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.StimuliFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StimuliFrameRepository extends JpaRepository<StimuliFrame, Long> {
}
