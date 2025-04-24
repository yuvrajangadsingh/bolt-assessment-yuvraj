package com.bolt.assessment.service;

import com.bolt.assessment.model.ApiSummary;
import com.bolt.assessment.model.ClassDetail;
import com.bolt.assessment.model.DndApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DndApiService {
    @Value("${dnd.api.base-url}")
    private String baseUrl;
    
    private final RestTemplate restTemplate;

    public DndApiService() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable("apiSummary")
    public ApiSummary getApiSummary() {
        ApiSummary summary = new ApiSummary();
        
        summary.setTotalClasses(getCount("/classes"));
        summary.setTotalSpells(getCount("/spells"));
        summary.setTotalMonsters(getCount("/monsters"));
        summary.setTotalFeatures(getCount("/features"));
        
        return summary;
    }

    @Cacheable(value = "classDetails", key = "#className")
    public ClassDetail getClassDetails(String className) {
        try {
            String url = baseUrl + "/classes/" + className;
            return restTemplate.getForObject(url, ClassDetail.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    @Cacheable(value = "apiCounts", key = "#endpoint")
    private int getCount(String endpoint) {
        String url = baseUrl + endpoint;
        DndApiResponse response = restTemplate.getForObject(url, DndApiResponse.class);
        return response != null ? response.getCount() : 0;
    }
} 