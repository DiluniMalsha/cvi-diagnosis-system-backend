package lk.ac.uwu.cvi.controller;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.request.DiagnosisRequestDTO;
import lk.ac.uwu.cvi.dto.request.DiagnosisScoreUpdateRequestDTO;
import lk.ac.uwu.cvi.dto.response.ResponseDTO;
import lk.ac.uwu.cvi.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
@CrossOrigin(origins = "http://localhost:5173")
public class DiagnoseController {

    private final DiagnosisService diagnosisService;

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createUpdate(@RequestBody DiagnosisRequestDTO request) {
        return ResponseEntity.ok(diagnosisService.createUpdate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.getById(id));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<ResponseDTO> getDiagnosesForPatient(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(true, "", diagnosisService.getDiagnosesForPatient(id)));
    }

    @PostMapping("/characteristic/start/{id}")
    public ResponseEntity<ResponseDTO> startDiagnosisCharacteristicTest(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.startDiagnosisCharacteristicTest(id));
    }

    @PostMapping("/phase/prediction/start/{id}")
    public ResponseEntity<ResponseDTO> startDiagnosisPhasePrediction(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.startDiagnosisPhasePrediction(id));
    }

    @GetMapping("/2811b9cf09898c74ad888e9bc844a24a/check")
    public ResponseEntity<ResponseDTO> check() {
        return ResponseEntity.ok(diagnosisService.checkDiagnosisConductStatus());
    }

    @PostMapping("/2811b9cf09898c74ad888e9bc844a24a/result")
    public ResponseEntity<ResponseDTO> result(@RequestBody DiagnosisCharacteristicResultRequestDTO request) {
        System.out.println("controller  - " + request);
        return ResponseEntity.ok(diagnosisService.endDiagnosisCharacteristicTest(request));
    }

    @GetMapping("/final/2811b9cf09898c74ad888e9bc844a24a/check")
    public ResponseEntity<ResponseDTO> checkPendingDiagnosisPhase() {
        return ResponseEntity.ok(diagnosisService.checkPendingDiagnosisPhase());
    }

    @PostMapping("/final/2811b9cf09898c74ad888e9bc844a24a/result")
    public ResponseEntity<ResponseDTO> updateDiagnosisPhase(@RequestBody DiagnosisScoreUpdateRequestDTO request) {
        System.out.println("Update Diagnosis Phase  - " + request);
        return ResponseEntity.ok(diagnosisService.updateDiagnosisPhase(request));
    }

}
