package lk.ac.uwu.cvi.repository;

import lk.ac.uwu.cvi.entity.Resource;
import lk.ac.uwu.cvi.entity.ResourceFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceFrameRepository extends JpaRepository<ResourceFrame, Long> {

    List<ResourceFrame> findAllByResourceOrderByTimeAsc(Resource resource);
}
