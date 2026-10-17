
package com.healthai.controller;

import com.healthai.dto.triage.HealthQueryRequest;
import com.healthai.dto.triage.TriageResponse;
import com.healthai.service.GeminiTriageService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/triage")
@CrossOrigin(origins = {
        "http://localhost",
        "http://localhost:80",
        "http://localhost:4200"
})
public class TriageController {

    private static final Logger log =
            LoggerFactory.getLogger(TriageController.class);

    private final GeminiTriageService triageService;

    // Replacement for Lombok @RequiredArgsConstructor
    public TriageController(GeminiTriageService triageService) {
        this.triageService = triageService;
    }

    @PostMapping("/assess")
    public ResponseEntity<TriageResponse> assessSymptoms(
            @Valid @RequestBody HealthQueryRequest request) {

        log.info(
                "Received triage assessment query. Length of symptoms: {}",
                request.getSymptoms().length()
        );
        System.out.println("Request for assess Symptoms: "+request.getSymptoms());

        TriageResponse response =
                triageService.evaluateSymptoms(request);
        System.out.println("Response: "+response.getCareAdvice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(
                "Health Triage API is fully operational."
        );
    }
}
