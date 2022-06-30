package edu.neu.csye6225.dao;

import edu.neu.csye6225.controller.ProfileImgaeController;
import edu.neu.csye6225.model.pojo.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Transactional
public class UserDao extends AbstractDao<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Transactional
    public User insertNewUser(User user){
        LOGGER.debug("Inserting new user record into database");
        em().persist(user);
        em().flush();
        return user;
    }

    @Transactional
    public void updateUser(User user){
        LOGGER.debug("Updating user record into database");
        em().merge(user);
        em().flush();
    }

    public User getUserByUsername(String username){
        LOGGER.debug("Fetching user record from database");
        String select_id = "Select u From User u where username=:username";
        TypedQuery<User> query = getQuery(select_id, User.class);
        query.setParameter("username", username);
        return getSingle(query);
    }
}
