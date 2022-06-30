package edu.neu.csye6225.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressData {
    private String id;
    private String user_id;
    private String zip_code;
    private String addressLine_1;
    private String addressLine_2;
    private String city;
    private String state;
    private String country;
}
