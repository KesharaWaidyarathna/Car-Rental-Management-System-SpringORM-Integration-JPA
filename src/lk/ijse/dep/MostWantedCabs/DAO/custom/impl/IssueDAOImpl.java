package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;
import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Issue;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueDAOImpl extends CrudDAOImpl<Issue,String> implements IssueDAO {


    @Override
    public String getLastIssueID() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT id FROM issue ORDER BY id DESC LIMIT 1");
        return nativeQuery.getResultList().size()>0? (String) nativeQuery.getSingleResult() :null;
    }

    @Override
    public boolean existCustomerId(String customerId) throws Exception {
        return entityManager.createNativeQuery("SELECT customerId FROM issue WHERE customerId=?1").setParameter(1, customerId).getResultList().size()>0;
    }

    @Override
    public boolean existVehicleId(String vehicleId) throws Exception {
        return entityManager.createNativeQuery("SELECT vehicleId FROM issue WHERE vehicleId=?1").setParameter(1, vehicleId).getResultList().size()>0;
    }

    @Override
    public List<Issue> findallIssueIds(String statues) throws Exception {
        return entityManager.createNativeQuery("SELECT * FROM Issue WHERE Issue.statues=?1").setParameter(1,statues).getResultList();
    }
}
