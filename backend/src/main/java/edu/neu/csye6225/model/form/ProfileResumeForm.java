package edu.neu.csye6225.model.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileResumeForm {
    private MultipartFile profileResume;
}
