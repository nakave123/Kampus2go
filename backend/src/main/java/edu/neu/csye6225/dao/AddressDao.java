package edu.neu.csye6225.dao;

import edu.neu.csye6225.model.pojo.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDao extends AbstractDao<Address>{

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDao.class);

    public Address getUserAddress(String user_id) {
        LOGGER.debug("Fetching address details of the user from database");
        String select_id = "Select a From Address a where user_id=:user_id";
        TypedQuery<Address> query = getQuery(select_id, Address.class);
        query.setParameter("user_id", user_id);
        return getSingle(query);
    }

    @Transactional
    public void addUserAddress(Address addressDetails) {
        LOGGER.debug("Adding address for the user to the database");
        em().persist(addressDetails);
        em().flush();
    }

    @Transactional
    public void updateUserAddress(Address addressDetails) {
        LOGGER.debug("Updating address of the user from database");
        em().merge(addressDetails);
        em().flush();
    }

    @Transactional
    public void deleteUserAddress(Address addressDetails) {
        LOGGER.debug("Deleting address of the user from database");
        em().remove(addressDetails);
        em().flush();
    }
}
