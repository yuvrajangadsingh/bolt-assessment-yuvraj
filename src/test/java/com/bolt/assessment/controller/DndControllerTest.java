package com.bolt.assessment.controller;

import com.bolt.assessment.model.ApiSummary;
import com.bolt.assessment.model.ClassDetail;
import com.bolt.assessment.service.DndApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DndControllerTest {

    @Mock
    private DndApiService dndApiService;

    @InjectMocks
    private DndController dndController;

    @Test
    void getSummary_ShouldReturnApiSummary() {
        // Create mock summary
        ApiSummary mockSummary = new ApiSummary();
        mockSummary.setTotalClasses(12);
        mockSummary.setTotalSpells(319);
        mockSummary.setTotalMonsters(334);
        mockSummary.setTotalFeatures(408);

        // Mock the service call
        when(dndApiService.getApiSummary()).thenReturn(mockSummary);

        // Call the endpoint
        ResponseEntity<ApiSummary> response = dndController.getSummary();

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(12, response.getBody().getTotalClasses());
        assertEquals(319, response.getBody().getTotalSpells());
        assertEquals(334, response.getBody().getTotalMonsters());
        assertEquals(408, response.getBody().getTotalFeatures());
    }

    @Test
    void getClassDetails_ShouldReturnClassDetails() {
        // Create mock class details
        ClassDetail mockClassDetail = new ClassDetail();
        mockClassDetail.setName("Barbarian");
        mockClassDetail.setHit_die(12);

        // Mock the service call
        when(dndApiService.getClassDetails("barbarian")).thenReturn(mockClassDetail);

        // Call the endpoint
        ResponseEntity<ClassDetail> response = dndController.getClassDetails("barbarian");

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Barbarian", response.getBody().getName());
        assertEquals(12, response.getBody().getHit_die());
    }
} 