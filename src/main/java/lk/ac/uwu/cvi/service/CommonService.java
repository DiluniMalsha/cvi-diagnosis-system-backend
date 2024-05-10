package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.request.RequestDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.exception.CustomServiceException;
import org.springframework.data.domain.Pageable;

public interface CommonService {
    ResponseDTO createUpdate(RequestDTO request);

    ResponseDTO getById(Long id);

    ResponseDTO search(RequestDTO request, Pageable pageable);

    default ResponseDTO getSuccessResponse(String message, Object body) {
        return new ResponseDTO(true, message, body);
    }

    default CustomServiceException generateCustomServiceException(Integer code, String message) {
        return new CustomServiceException(code, message);
    }

    default CustomServiceException generateNotFoundException(String entity) {
        return new CustomServiceException(404, entity + " Not Found!");
    }
}
