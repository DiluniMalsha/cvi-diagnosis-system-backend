package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PatientService extends CommonService {

    ResponseDTO getNextRegistrationId();
}
