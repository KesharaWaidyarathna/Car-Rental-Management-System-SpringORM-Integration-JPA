package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.CustomerDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;

import javax.persistence.Query;

public class CustomerDAOImpl extends CrudDAOImpl<Customer,String> implements CustomerDAO {

    @Override
    public String getLasCustomerId() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size() > 0? (String) nativeQuery.getSingleResult() : null;

    }
}
