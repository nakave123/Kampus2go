package edu.neu.csye6225.controller;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import edu.neu.csye6225.model.data.UserData;
import edu.neu.csye6225.model.form.UserForm;
import edu.neu.csye6225.service.UserDto;
import edu.neu.csye6225.util.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RestController
public class UserController extends AbstractController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StatsDClient metricClient;

    @Autowired
    private UserDto userDto;

    @SuppressWarnings("deprecation")
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return (WebMvcConfigurer) new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

    @PostMapping(value = "/v1/user", consumes={"application/json"})
    @ResponseBody
    public UserData createNewUser(@RequestBody UserForm userForm) throws ApiException {
        metricClient.incrementCounter("endpoint.v1.user.http.post");
        UserData userData = userDto.createUser(userForm);
        LOGGER.info("New user created");
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).withRegion(Regions.US_EAST_1).build();

        CreateTopicResult topicResult = snsClient.createTopic("email");
        String topicArn = topicResult.getTopicArn();

        final PublishRequest publishRequest = new PublishRequest(topicArn, userData.getUsername());
        LOGGER.info("Reset request made"+publishRequest.getMessage());
        final PublishResult publishResponse = snsClient.publish(publishRequest);
        return userData;
    }

    @PutMapping(value = "/v1/user/self", consumes={"application/json"})
    public ResponseEntity<String> updateUserDetails(@RequestBody UserForm userForm) throws ApiException {
        metricClient.incrementCounter("endpoint.v1.user.self.http.put");
        userDto.updateUser(userForm);
        LOGGER.info("User updated with new details");
        return new ResponseEntity<>(null, getHeaders(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/v1/user/self")
    @ResponseBody
    public UserData getUserDetails() throws ApiException {
        metricClient.incrementCounter("endpoint.v1.user.self.http.get");
        LOGGER.info("Getting user details");
        return userDto.getUserDetails();
    }

    @ApiOperation(value = "Verify User Email")
    @GetMapping(value = "/v1/verifyUserEmail")
    public UserData accountVerification(@RequestParam(name="email") String email, @RequestParam(name = "token") String token) throws ApiException {
        metricClient.incrementCounter("Verify New User API");
        LOGGER.info("Verify New User API called.");
        return userDto.checkValidToken(email, token);
//        return new ResponseEntity<>("Your account is verified!!", getHeaders(), HttpStatus.OK);
    }
}
