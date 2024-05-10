package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Stimuli;
import lk.ac.uwu.cvi.enums.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StimuliRepository extends JpaRepository<Stimuli, Long> {

    Optional<Stimuli> findByIdAndCharacteristic(Long id, Characteristic characteristic);
}
