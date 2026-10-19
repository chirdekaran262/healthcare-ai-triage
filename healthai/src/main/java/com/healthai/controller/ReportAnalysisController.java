
package com.healthai.controller;

import com.healthai.dto.report.ReportAnalysisResponse;
import com.healthai.service.ReportAnalysisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin(origins = {
        "http://localhost",
        "http://localhost:80",
        "http://localhost:4200"
})
public class ReportAnalysisController {

    private static final Logger log =
            LoggerFactory.getLogger(ReportAnalysisController.class);

    private final ReportAnalysisService reportAnalysisService;

    // Constructor injection replaces @RequiredArgsConstructor
    public ReportAnalysisController(
            ReportAnalysisService reportAnalysisService) {
        this.reportAnalysisService = reportAnalysisService;
    }

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ReportAnalysisResponse> analyzeMedicalReport(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "patientContext", required = false)
            String patientContext) {

        log.info(
                "Analyzing medical report: name={}, size={} bytes, mimeType={}",
                file != null ? file.getOriginalFilename() : "unknown",
                file != null ? file.getSize() : 0,
                file != null ? file.getContentType() : "unknown"
        );

        ReportAnalysisResponse result =
                reportAnalysisService.analyzeReport(file, patientContext);

        return ResponseEntity.ok(result);
    }
}
