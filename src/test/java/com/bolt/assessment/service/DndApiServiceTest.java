package com.bolt.assessment.service;

import com.bolt.assessment.model.ApiSummary;
import com.bolt.assessment.model.ClassDetail;
import com.bolt.assessment.model.DndApiResponse;
import com.bolt.assessment.model.Proficiency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DndApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DndApiService dndApiService;

    @BeforeEach
    void setUp() {
        dndApiService = new DndApiService();
        // Set the baseUrl property
        ReflectionTestUtils.setField(dndApiService, "baseUrl", "https://www.dnd5eapi.co/api/2014");
    }

    @Test
    void getApiSummary_ShouldReturnCorrectCounts() {
        // Mock responses for each endpoint
        DndApiResponse classesResponse = new DndApiResponse();
        classesResponse.setCount(12);
        
        DndApiResponse spellsResponse = new DndApiResponse();
        spellsResponse.setCount(319);
        
        DndApiResponse monstersResponse = new DndApiResponse();
        monstersResponse.setCount(334);
        
        DndApiResponse featuresResponse = new DndApiResponse();
        featuresResponse.setCount(370);

        // Mock the RestTemplate calls with full URLs
        when(restTemplate.getForObject(eq("https://www.dnd5eapi.co/api/2014/classes"), eq(DndApiResponse.class)))
                .thenReturn(classesResponse);
        when(restTemplate.getForObject(eq("https://www.dnd5eapi.co/api/2014/spells"), eq(DndApiResponse.class)))
                .thenReturn(spellsResponse);
        when(restTemplate.getForObject(eq("https://www.dnd5eapi.co/api/2014/monsters"), eq(DndApiResponse.class)))
                .thenReturn(monstersResponse);
        when(restTemplate.getForObject(eq("https://www.dnd5eapi.co/api/2014/features"), eq(DndApiResponse.class)))
                .thenReturn(featuresResponse);

        // Call the method
        ApiSummary summary = dndApiService.getApiSummary();

        // Verify the results
        assertEquals(12, summary.getTotalClasses());
        assertEquals(319, summary.getTotalSpells());
        assertEquals(334, summary.getTotalMonsters());
        assertEquals(370, summary.getTotalFeatures());
    }

    @Test
    void getClassDetails_ShouldReturnClassDetails() {
        // Create mock class details
        ClassDetail mockClassDetail = new ClassDetail();
        mockClassDetail.setName("Barbarian");
        mockClassDetail.setHit_die(12);
        
        // Create mock proficiencies
        Proficiency strProficiency = new Proficiency();
        strProficiency.setIndex("str");
        strProficiency.setName("STR");
        strProficiency.setUrl("/api/2014/ability-scores/str");
        
        Proficiency conProficiency = new Proficiency();
        conProficiency.setIndex("con");
        conProficiency.setName("CON");
        conProficiency.setUrl("/api/2014/ability-scores/con");
        
        mockClassDetail.setSaving_throws(Arrays.asList(strProficiency, conProficiency));

        // Mock the RestTemplate call with full URL
        when(restTemplate.getForObject(
                eq("https://www.dnd5eapi.co/api/2014/classes/barbarian"), 
                eq(ClassDetail.class)))
                .thenReturn(mockClassDetail);

        // Call the method
        ClassDetail result = dndApiService.getClassDetails("barbarian");

        // Verify the results
        assertNotNull(result);
        assertEquals("Barbarian", result.getName());
        assertEquals(12, result.getHit_die());
        assertEquals(2, result.getSaving_throws().size());
        assertEquals("STR", result.getSaving_throws().get(0).getName());
        assertEquals("CON", result.getSaving_throws().get(1).getName());
    }

    @Test
    void getClassDetails_WhenApiFails_ShouldReturnNull() {
        // Mock the RestTemplate to throw a 404 exception
        when(restTemplate.getForObject(
                eq("https://www.dnd5eapi.co/api/2014/classes/invalid-class"), 
                eq(ClassDetail.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Call the method
        ClassDetail result = dndApiService.getClassDetails("invalid-class");

        // Verify the result is null
        assertNull(result);
    }
} 