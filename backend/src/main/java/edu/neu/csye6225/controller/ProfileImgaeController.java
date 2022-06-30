package edu.neu.csye6225.controller;

import edu.neu.csye6225.service.ProfileDto;
import edu.neu.csye6225.model.data.ProfileImageData;
import edu.neu.csye6225.util.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@RestController
public class ProfileImgaeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileImgaeController.class);

    @Autowired
    private StatsDClient metricClient;

    @Autowired
    private ProfileDto profileDto;

    @ApiOperation(value = "Add or update a profile image")
    @PostMapping(value = "/v1/user/self/pic", consumes={"multipart/form-data", MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProfileImageData createOrUpdateProfilePic(MultipartFile file) throws ApiException {
        metricClient.incrementCounter("endpoint.v1.user.self.pic.http.post");
        LOGGER.info("Adding profile pic for the user");
        return profileDto.addOrUpdateProfileImage(file);
    }

//    @ApiOperation(value = "Add or update a profile image")
//    @PostMapping(value = "/v1/user/self/pic", consumes={MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
//    public ProfileImageData createOrUpdateProfilePic(InputStream dataStream) throws ApiException {
//        return profileDto.addOrUpdateProfileImage(dataStream);
//    }

    @ApiOperation(value = "Delete profile image")
    @DeleteMapping(value = "/v1/user/self/pic", consumes={MediaType.ALL_VALUE})
    public ResponseEntity<String> deleteProfilePic() throws ApiException, UnsupportedEncodingException {
        metricClient.incrementCounter("endpoint.v1.user.self.pic.http.delete");
        LOGGER.info("Deleting profile pic for the user");
        profileDto.deleteProfileImage();
        return new ResponseEntity<>(null, getHeaders(), HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get profile image")
    @GetMapping(value = "/v1/user/self/pic")
    public ProfileImageData getProfileImage() throws ApiException {
        metricClient.incrementCounter("endpoint.v1.user.self.pic.http.get");
        LOGGER.info("Getting profile pic for the user");
        return profileDto.getProfileImage();
    }
}
