package edu.neu.csye6225.model.form;

import edu.neu.csye6225.model.pojo.Address;
import edu.neu.csye6225.model.pojo.Education;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserForm {

    //data coming from client
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private long mobileNo;
}
