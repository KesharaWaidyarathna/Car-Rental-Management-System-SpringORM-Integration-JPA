package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;
import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.OwnerDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class OwnerDAOImpl extends CrudDAOImpl<Owner,String> implements OwnerDAO {

    @Override
    public String getLastOwnerID() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM owner ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size()>0? (String) nativeQuery.getSingleResult() :null;
    }
}
