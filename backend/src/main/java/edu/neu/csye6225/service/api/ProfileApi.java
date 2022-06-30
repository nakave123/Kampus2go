package edu.neu.csye6225.service.api;

import edu.neu.csye6225.dao.ProfileDao;
import edu.neu.csye6225.model.pojo.Image;
import edu.neu.csye6225.model.pojo.User;
import edu.neu.csye6225.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProfileApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileApi.class);

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private AWSS3Api awss3Api;

    @Autowired
    private UserService userApi;

    public Image addUpdateImageDetails(String username, MultipartFile file) throws ApiException {
        // Get User details
        User user = userApi.getUserDetails(username);

        String foldername = user.getId();

        // Check existing file record in Image table
        Image profileInDB = profileDao.getProfileImage(user.getId());
        if (profileInDB == null) {
            // Upload file to s3
            final URL url = awss3Api.uploadFile(file, foldername);
            try {
                LOGGER.info("Setting image details");
                return addImageDetails(url, user, file.getOriginalFilename());
            } catch (Exception e) {
                LOGGER.warn("Exception occurred while adding image details.");
                awss3Api.deleteFile(user.getId()+"/"+file.getOriginalFilename());
                LOGGER.warn("Deleted the image as exception was occured while adding details");
                throw new ApiException("Exception occurred while adding image details.");
            }
        }
        if (file.getOriginalFilename().equalsIgnoreCase(profileInDB.getFile_name())) {
            LOGGER.warn("Profile Pic with same filename exist!!");
            throw new ApiException("Profile Pic with same filename exist!!");
        }
        // Upload file to s3
        final URL url = awss3Api.uploadFile(file, foldername);

        Image updateImageDetails;
        try {
                awss3Api.deleteFile(user.getId()+"/"+profileInDB.getFile_name());
                updateImageDetails(profileInDB, url, file.getOriginalFilename());
                updateImageDetails = profileDao.getProfileImage(user.getId());
        } catch(Exception e) {
            LOGGER.warn("Exception occurred while updating image details.");
            throw new ApiException("Exception occurred while updating image details.");
        }
        return updateImageDetails;
    }

    private void updateImageDetails(Image imageDetails, URL url, String name) {
        imageDetails.setUrl(String.valueOf(url));
        imageDetails.setUpload_date(new Date());
        imageDetails.setFile_name(name);
        profileDao.updateImageDetails(imageDetails);
    }

    private Image addImageDetails(URL url, User user, String name) {
        Image imageDetails = new Image();
        imageDetails.setUrl(String.valueOf(url));
        imageDetails.setUpload_date(new Date());
        imageDetails.setFile_name(name);
        imageDetails.setUser_id(user.getId());
        profileDao.addImageDetails(imageDetails);
        LOGGER.info("Set image details");
        return profileDao.getProfileImage(user.getId());
    }

    public void deleteImage(String username) throws ApiException, UnsupportedEncodingException {
        User user = userApi.getUserDetails(username);
        Image profileInDB = profileDao.getProfileImage(user.getId());
        if(profileInDB==null) {
            LOGGER.warn("No Profile Image found.");
            throw new ResponseStatusException(NOT_FOUND, "No Profile Image found.");
        }
        try {
            LOGGER.info("Deleting image in progress");
            awss3Api.deleteFile(user.getId()+"/"+profileInDB.getFile_name());
            profileDao.deleteImageDetails(profileInDB);
        }catch (Exception e ) {
            LOGGER.warn("Exception occurred while deleting image details.");
            throw new ApiException("Exception occurred while deleting image details.");
        }
    }

    public Image getImageDetails(String username) {
        User user = userApi.getUserDetails(username);
        Image profileImage = profileDao.getProfileImage(user.getId());
        LOGGER.info("Getting image details");
        if(profileImage == null) {
            LOGGER.warn("No Profile Image found.");
            throw new ResponseStatusException(NOT_FOUND, "No Profile Picture found");
        }
        return profileImage;
    }
}
