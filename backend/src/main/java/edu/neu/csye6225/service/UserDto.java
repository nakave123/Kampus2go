package edu.neu.csye6225.service;

import edu.neu.csye6225.model.pojo.Account;
import edu.neu.csye6225.model.pojo.User;
import edu.neu.csye6225.model.data.UserData;
import edu.neu.csye6225.model.form.UserForm;
import edu.neu.csye6225.service.api.MyUserDetails;
import edu.neu.csye6225.service.api.UserService;
import edu.neu.csye6225.service.helper.DtoHelper;
import edu.neu.csye6225.service.helper.UserDtoHelper;
import edu.neu.csye6225.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDto.class);

    @Autowired
    private UserService userApi;

    @Autowired
    private UserDtoHelper dtoHelper;

    @Autowired
    MyUserDetails userDetails;

    public UserData createUser(UserForm userForm) throws ApiException {
        // TODO Validations for userForm
        LOGGER.info("Creating a user in progress");
        User user = dtoHelper.convertToUser(userForm);
        User newUser = userApi.createUser(user);
        return dtoHelper.convertToUserData(newUser);
    }

    public void updateUser(UserForm userForm) throws ApiException {
        // TODO Validations for userForm and username
        LOGGER.info("Updating a user in progress");
        User user = dtoHelper.convertToUserUpdate(userForm);
        String username = DtoHelper.getUsername();
        User userDb = userApi.getUserDetails(username);
        if(userDb==null){
            LOGGER.warn("User is not found");
            throw new ApiException("User is not found");
        }
        if (!userDb.getIsActive()) {
            LOGGER.info("User "+userDb.getUsername()+" is not authenticated");
            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
        }
        userApi.updateUser(user);
    }

    public UserData getUserDetails() throws ApiException {
        // TODO Validations for username
        String username = DtoHelper.getUsername();
        User user = userApi.getUserDetails(username);
        if(user==null){
            LOGGER.warn("User is not found");
            throw new ApiException("User is not found");
        }
        if (!user.getIsActive()) {
            LOGGER.info("User "+user.getUsername()+" is not authenticated");
            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
        }
        LOGGER.info("Fetching a user details in progress");
        return dtoHelper.convertToUserData(user);
    }

    public UserData checkValidToken(String email, String token) throws ApiException {
        User user = userApi.getUserDetails(email);
        if(user==null){
            LOGGER.warn("User is not found");
            throw new ApiException("User is not found");
        }
//        if (!user.getIsActive()) {
//            LOGGER.info("User "+user.getUsername()+" is not authenticated");
//            throw new ApiException("User not authenticated yet!! Please complete activation process first.");
//        }
        Account account = userApi.verifyAccount(email, token);
        if(account == null) {
            throw new ApiException("Invalid email: " + email + " or token: " + token);
        }
        LOGGER.info("User: "+account.getUsername()+" is validated!!");
        user.setIsActive(true);
        userApi.updateUser(user);
        return dtoHelper.convertToUserData(user);
    }
}
