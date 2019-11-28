package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.DriverDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl extends CrudDAOImpl<Driver,String> implements DriverDAO {

    @Override
    public String getLastDriverID() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM driver ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size() > 0? (String) nativeQuery.getSingleResult() : null ;
    }

    @Override
    public List<String> availableDrivers(String statues) throws Exception {

        Query query = entityManager.createNativeQuery("SELECT id FROM driver WHERE statues=?1").setParameter(1, statues);
        return query.getResultList();
    }

    @Override
    public boolean existVehicleId(String driverId) throws Exception {
         return entityManager.createQuery("SELECT issue FROM Driver WHERE issue.driver.id=?1").setParameter(1,driverId).getResultList().size()>0;
    }

    @Override
    public String getDriverId(String issueId) throws Exception {
       return String.valueOf(entityManager.createQuery("SELECT issue.driver.id FROM Driver WHERE issue.id=?1").setParameter(1,issueId));
    }
}
