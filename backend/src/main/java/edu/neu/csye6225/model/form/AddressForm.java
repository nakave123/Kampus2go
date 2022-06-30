package edu.neu.csye6225.model.form;

import lombok.Getter;

@Getter
public class AddressForm {
    private String addressLine_1;
    private String addressLine_2;
    private String city;
    private String state;
    private String country;
    private long zip_code;
}
