package com.bolt.assessment.model;

import lombok.Data;
import java.util.List;

@Data
public class DndApiResponse {
    private int count;
    private List<DndResource> results;
}
