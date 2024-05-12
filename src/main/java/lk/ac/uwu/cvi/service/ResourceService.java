package lk.ac.uwu.cvi.service;

import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.enums.Characteristic;

public interface ResourceService extends CommonService {
    ResponseDTO getByCharacteristic(Characteristic characteristic);
}
