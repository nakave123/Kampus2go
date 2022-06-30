package edu.neu.csye6225.service;

import edu.neu.csye6225.service.api.AWSS3Api;
import edu.neu.csye6225.service.api.ProfileApi;
import edu.neu.csye6225.service.api.UserService;
import edu.neu.csye6225.service.helper.DtoHelper;
import edu.neu.csye6225.service.helper.ProfileDtoHelper;
import edu.neu.csye6225.model.data.ProfileImageData;
import edu.neu.csye6225.model.form.ProfileImageForm;
import edu.neu.csye6225.model.pojo.Image;
import edu.neu.csye6225.model.pojo.User;
import edu.neu.csye6225.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProfileDto {
    @Autowired
    private ProfileApi profileApi;

    @Autowired
    private UserService userApi;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDto.class);

    @Transactional(rollbackFor = ApiException.class)
    public ProfileImageData addOrUpdateProfileImage(MultipartFile multipartFile) throws ApiException {
        String username = DtoHelper.getUsername();
        User user = userApi.getUserDetails(username);
        if(user==null){
            LOGGER.warn("User "+user.getUsername()+" is not found");
            throw new ApiException("User "+user.getUsername()+" is not found");
        }
        if (!user.getIsActive()) {
            LOGGER.info("User "+user.getUsername()+" is not authenticated");
            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
        }
        //File file = DtoHelper.convertMultiPartFileToFile(multipartFile)
        LOGGER.info("Adding/updating profile pic is in progress");
        Image image = profileApi.addUpdateImageDetails(username, multipartFile);
        return ProfileDtoHelper.convertToProfileImageData(image);
    }

//    @Transactional(rollbackFor = ApiException.class)
//    public ProfileImageData addOrUpdateProfileImage(InputStream inputStream) throws ApiException {
//        String username = DtoHelper.getUsername();
//        File file = DtoHelper.convertToFile(inputStream);
//        Image image = profileApi.addUpdateImageDetails(username, file);
//        return ProfileDtoHelper.convertToProfileImageData(image);
//    }

    public void deleteProfileImage() throws ApiException, UnsupportedEncodingException {
        String username = DtoHelper.getUsername();
        LOGGER.info("Deleting profile pic is in progress");
        User user = userApi.getUserDetails(username);
        if(user==null){
            LOGGER.warn("User "+user.getUsername()+" is not found");
            throw new ApiException("User "+user.getUsername()+" is not found");
        }
        if (!user.getIsActive()) {
            LOGGER.info("User "+user.getUsername()+" is not authenticated");
            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
        }
        profileApi.deleteImage(username);
    }

    public ProfileImageData getProfileImage() throws ApiException {
        String username = DtoHelper.getUsername();
        User user = userApi.getUserDetails(username);
        if(user==null){
            LOGGER.warn("User "+user.getUsername()+" is not found");
            throw new ApiException("User "+user.getUsername()+" is not found");
        }
        if (!user.getIsActive()) {
            LOGGER.info("User "+user.getUsername()+" is not authenticated");
            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
        }
        LOGGER.info("Fetching profile pic is in progress");
        Image image = profileApi.getImageDetails(username);
        return ProfileDtoHelper.convertToProfileImageData(image);
    }
}
