package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.ResourceResponseDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.repository.ResourceRepository;
import lk.ac.uwu.cvi.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public ResponseDTO createUpdate(RequestDTO request) {
        return null;
    }

    @Override
    public ResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public ResponseDTO search(RequestDTO request, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseDTO getByCharacteristic(Characteristic characteristic) {
        return this.getSuccessResponse("Success",
                resourceRepository.findByCharacteristic(characteristic).stream()
                        .map(r -> new ResourceResponseDTO(r.getId(), r.getCharacteristic(), r.getTotalTime(), r.getResourceName(), r.getResourceUrl())).toList());
    }
}
