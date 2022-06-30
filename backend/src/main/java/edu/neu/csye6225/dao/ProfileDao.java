package edu.neu.csye6225.dao;

import edu.neu.csye6225.controller.ProfileImgaeController;
import edu.neu.csye6225.model.pojo.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Transactional
public class ProfileDao extends AbstractDao<Image>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDao.class);

    public Image getProfileImage(String user_id) {
        LOGGER.debug("Fetching profile image of the user from database");
        String select_id = "Select i From Image i where user_id=:user_id";
        TypedQuery<Image> query = getQuery(select_id, Image.class);
        query.setParameter("user_id", user_id);
        return getSingle(query);
    }

    @Transactional
    public void addImageDetails(Image imageDetails) {
        LOGGER.debug("Adding profile image for the user to the database");
        em().persist(imageDetails);
        em().flush();
    }

    @Transactional
    public void updateImageDetails(Image imageDetails) {
        LOGGER.debug("Updating profile image of the user from database");
        em().merge(imageDetails);
        em().flush();
    }

    @Transactional
    public void deleteImageDetails(Image profileInDB) {
        LOGGER.debug("Deleting profile image of the user from database");
        em().remove(profileInDB);
        em().flush();
    }
}
