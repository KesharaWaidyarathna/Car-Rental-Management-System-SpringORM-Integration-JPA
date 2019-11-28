package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleCategoryDAO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryDAOImpl extends CrudDAOImpl<VehicleCategory,String> implements VehicleCategoryDAO {

    @Override
    public String getLastCategoryID() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM vehiclecategory ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size()>0? (String) nativeQuery.getSingleResult() :null;
    }
}
