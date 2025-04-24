package com.bolt.assessment.model;

import lombok.Data;
import com.bolt.assessment.model.From;

@Data
public class ProficiencyChoice {
    private String desc;
    private int choose;
    private String type;
    private From from;
} 