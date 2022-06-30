package edu.neu.csye6225.service.helper;

import edu.neu.csye6225.model.data.UserData;
import edu.neu.csye6225.model.form.UserForm;
import edu.neu.csye6225.model.pojo.Address;
import edu.neu.csye6225.util.UtilHelper;
import edu.neu.csye6225.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoHelper {
    @Autowired
    PasswordEncoder passwordEncoder;

    public User convertToUser(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setFirst_name(userForm.getFirst_name());
        user.setLast_name(userForm.getLast_name());
        user.setIsActive(false);
        return user;
    }

    public UserData convertToUserData(User newUser) {
        UserData userData = new UserData();
        userData.setUsername(newUser.getUsername());
        userData.setId(newUser.getId());
        userData.setFirst_name(newUser.getFirst_name());
        userData.setLast_name(newUser.getLast_name());
        userData.setIsActive(newUser.getIsActive());
        userData.setAccount_created(UtilHelper.convertToDatetimeString(newUser.getAccount_created()));
        userData.setAccount_updated(UtilHelper.convertToDatetimeString(newUser.getAccount_updated()));
        return userData;
    }

    public User convertToUserUpdate(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setFirst_name(userForm.getFirst_name());
        user.setLast_name(userForm.getLast_name());
        user.setDate_of_birth(userForm.getDate_of_birth());
        user.setMobileNo(userForm.getMobileNo());
        return user;
    }


}

