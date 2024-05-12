package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.ResourceFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceFrameRepository extends JpaRepository<ResourceFrame, Long> {
}
