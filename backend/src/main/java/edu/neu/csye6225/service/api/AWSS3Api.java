package edu.neu.csye6225.service.api;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import edu.neu.csye6225.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AWSS3Api {

    private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Api.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // @Async annotation ensures that the method is executed in a different background thread
    // but not consume the main thread.
    @Async
    public URL uploadFile(MultipartFile file, String foldername) throws ApiException {
        URL url = null;
        try {
            LOGGER.info("uploading a file to AWS S3 bucket");
            url = uploadFileToS3Bucket(bucketName, file, foldername);
            LOGGER.info("uploaded a file to AWS S3 bucket");
//            file.delete();  // To remove the file locally created in the project folder.
        } catch (final AmazonServiceException ex) {
            LOGGER.warn("File upload is failed." + ex.getMessage());
            throw new ApiException("File upload is failed."+ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    private URL uploadFileToS3Bucket(final String bucketName, MultipartFile file, String foldername) throws IOException {
        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());
        amazonS3.putObject(bucketName, foldername+"/"+file.getOriginalFilename(), file.getInputStream(), data);
        return amazonS3.getUrl(bucketName, uniqueFileName);
    }

    @Async
    public void deleteFile(final String keyName) {
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        LOGGER.info("Deleting the file from AWS S3 bucket");
        amazonS3.deleteObject(deleteObjectRequest);
        LOGGER.info("Deleted the file from AWS S3 bucket");
    }
}
