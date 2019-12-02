package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDAOImpl extends CrudDAOImpl<Vehicle,String> implements VehicleDAO {

    @Override
    public String getLastVehicleID() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM vehicle ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size()>0? (String) nativeQuery.getSingleResult() :null;
    }

    @Override
    public boolean existVehicleCategoryId(String vehicleCategoryId) throws Exception {
        return entityManager.createNativeQuery("SELECT categoryId FROM vehicle WHERE categoryId=?1").setParameter(1,vehicleCategoryId).getResultList().size()>0;
    }

    @Override
    public boolean existOwnerId(String ownerId) throws Exception {
       return entityManager.createNativeQuery("SELECT ownerId FROM vehicle WHERE ownerId=?1").setParameter(1,ownerId).getResultList().size()>0;
    }

    @Override
    public List<String> availableVehicles(String statues) throws Exception {
        return entityManager.createNativeQuery("SELECT id FROM vehicle WHERE statues=?1").setParameter(1,statues).getResultList();
    }
}
