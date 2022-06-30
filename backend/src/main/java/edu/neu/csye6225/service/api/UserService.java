package edu.neu.csye6225.service.api;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import edu.neu.csye6225.dao.UserDao;
import edu.neu.csye6225.model.pojo.Account;
import edu.neu.csye6225.model.pojo.User;
import edu.neu.csye6225.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Transactional(rollbackFor = ApiException.class)
    public User createUser(User user) throws ApiException {
        User userDB = userDao.getUserByUsername(user.getUsername());
        if(userDB!=null) {
            System.out.println("User already exists");
            throw new ApiException("User already exists");
        }
        return userDao.insertNewUser(user);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void updateUser(User user) throws ApiException {
        User userDB = userDao.getUserByUsername(user.getUsername());
        if(userDB==null) {
            throw new ApiException("No user exists");
        }
//        if(!user.getUsername().equals(userDB.getUsername())) {
//            throw new ApiException("Username modification not allowed");
//        }
        userDB.setFirst_name(user.getFirst_name());
        userDB.setLast_name(user.getLast_name());
        userDB.setPassword(user.getPassword());
        userDB.setIsActive(true);
        userDao.updateUser(userDB);
    }

    @Transactional(readOnly = true)
    public User getUserDetails(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userCredentials = userDao.getUserByUsername(username); //database
        return new MyUserDetails(userCredentials);
    }

    public Account verifyAccount(String email, String token) {
        Account account;
        try {
//            LOGGER.info("verifying account info");
            account = dynamoDBMapper.load(Account.class, email, token);
            if(account.getToken().equals(token)) {
//                logger.info("Email: "+email+" is verified!!");
            }else {
//                logger.info("Invalid token");
            }
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), "Amazon DynamoDB Service error !! \nStackTrace: \n" + e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR while connecting Amazon DynamoDB !! \nStackTrace: \n"+ e.getMessage(), e);
        }
        return account;
    }
}
