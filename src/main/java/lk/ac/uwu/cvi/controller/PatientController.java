package lk.ac.uwu.cvi.controller;

import lk.ac.uwu.cvi.dto.request.PatientRequestDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("")
    public ResponseEntity<ResponseDTO> createUpdate(@RequestBody PatientRequestDTO request) {
        return ResponseEntity.ok(patientService.createUpdate(request));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/search")
    public ResponseEntity<ResponseDTO> getById(@RequestBody PatientRequestDTO request, Pageable pageable) {
        return ResponseEntity.ok(patientService.search(request, pageable));
    }

    @GetMapping("/next-reg-id")
    public ResponseEntity<ResponseDTO> getNextRegistrationId() {
        return ResponseEntity.ok(patientService.getNextRegistrationId());
    }
}
