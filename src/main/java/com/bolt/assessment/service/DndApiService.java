package com.bolt.assessment.service;

import com.bolt.assessment.model.ApiSummary;
import com.bolt.assessment.model.DndApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DndApiService {
    private static final String BASE_URL = "https://www.dnd5eapi.co/api/2014";
    private final RestTemplate restTemplate;

    public DndApiService() {
        this.restTemplate = new RestTemplate();
    }

    public ApiSummary getApiSummary() {
        ApiSummary summary = new ApiSummary();
        
        summary.setTotalClasses(getCount("/classes"));
        summary.setTotalSpells(getCount("/spells"));
        summary.setTotalMonsters(getCount("/monsters"));
        summary.setTotalFeatures(getCount("/features"));
        
        return summary;
    }

    private int getCount(String endpoint) {
        String url = BASE_URL + endpoint;
        DndApiResponse response = restTemplate.getForObject(url, DndApiResponse.class);
        return response != null ? response.getCount() : 0;
    }
} 