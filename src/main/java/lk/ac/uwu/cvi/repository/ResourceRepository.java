package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Resource;
import lk.ac.uwu.cvi.enums.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Optional<Resource> findByIdAndCharacteristic(Long id, Characteristic characteristic);
}
