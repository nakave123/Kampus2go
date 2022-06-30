package edu.neu.csye6225.service.helper;

import edu.neu.csye6225.model.data.ProfileImageData;
import edu.neu.csye6225.model.pojo.Image;

public class ProfileDtoHelper {
    public static ProfileImageData convertToProfileImageData(Image image) {
        ProfileImageData profileImageData = new ProfileImageData();
        profileImageData.setId(image.getId());
        profileImageData.setFile_name(image.getFile_name());
        profileImageData.setUser_id(image.getUser_id());
        profileImageData.setUrl(image.getUrl());
        if(image.getUpload_date() != null) {
            profileImageData.setUpload_date(image.getUpload_date().toString());
        }
        return profileImageData;
    }
}
