package edu.neu.csye6225.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileImageData {

    private String id;
    private String user_id;
    private String file_name;
    private String url;
    private String upload_date;
}
