package lk.ac.uwu.cvi.controller;

import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/{characteristic}")
    public ResponseEntity<ResponseDTO> getByCharacteristic(@PathVariable Characteristic characteristic) {
        return ResponseEntity.ok(resourceService.getByCharacteristic(characteristic));
    }
}
