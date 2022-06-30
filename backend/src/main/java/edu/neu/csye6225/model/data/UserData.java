package edu.neu.csye6225.model.data;

import edu.neu.csye6225.model.pojo.Address;
import edu.neu.csye6225.model.pojo.Education;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class UserData {

    //data to be sent in json format to client
    private String id;
    private String username;
    private String first_name;
    private String last_name;
    private String account_created;
    private String account_updated;
    private Boolean isActive;
    private Date date_of_birth;
    private long mobileNo;
}
