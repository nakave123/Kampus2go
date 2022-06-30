package edu.neu.csye6225.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationData {
    private String id;
    private String user_id;
    private String universityName;
    private String highestDegree;
    private int workExperiences;
    private String skillsSet;
    private String linkedInURL;
    private String gitHubURL;
    private String portfolioURL;
}
