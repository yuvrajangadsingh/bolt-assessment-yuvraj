package com.bolt.assessment.model;

import lombok.Data;
import java.util.List;

@Data
public class From {
    private String option_set_type;
    private List<Option> options;
} 