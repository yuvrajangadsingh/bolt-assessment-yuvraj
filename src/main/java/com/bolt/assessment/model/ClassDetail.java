package com.bolt.assessment.model;

import lombok.Data;
import java.util.List;
import com.bolt.assessment.model.Proficiency; 
import com.bolt.assessment.model.ProficiencyChoice; 

@Data
public class ClassDetail {
    private String name;
    private int hit_die;
    private List<ProficiencyChoice> proficiency_choices;
    private List<Proficiency> saving_throws;
}
