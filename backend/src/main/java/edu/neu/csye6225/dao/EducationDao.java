package edu.neu.csye6225.dao;

import edu.neu.csye6225.model.pojo.Address;
import edu.neu.csye6225.model.pojo.Education;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class EducationDao extends AbstractDao<Education>{

    private static final Logger LOGGER = LoggerFactory.getLogger(EducationDao.class);

    public Education getUserEducation(String user_id) {
        LOGGER.debug("Fetching education details of the user from database");
        String select_id = "Select e From Education e where user_id=:user_id";
        TypedQuery<Education> query = getQuery(select_id, Education.class);
        query.setParameter("user_id", user_id);
        return getSingle(query);
    }

    @Transactional
    public void addUserEducation(Education educationDetails) {
        LOGGER.debug("Adding education for the user to the database");
        em().persist(educationDetails);
        em().flush();
    }

    @Transactional
    public void updateUserEducation(Education educationDetails) {
        LOGGER.debug("Updating education of the user from database");
        em().merge(educationDetails);
        em().flush();
    }

    @Transactional
    public void deleteUserEducation(Education educationDetails) {
        LOGGER.debug("Deleting education of the user from database");
        em().remove(educationDetails);
        em().flush();
    }
}
