package com.bolt.assessment.controller;

import com.bolt.assessment.model.ApiSummary;
import com.bolt.assessment.service.DndApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DndController {
    private final DndApiService dndApiService;

    public DndController(DndApiService dndApiService) {
        this.dndApiService = dndApiService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiSummary> getSummary() {
        return ResponseEntity.ok(dndApiService.getApiSummary());
    }
} 