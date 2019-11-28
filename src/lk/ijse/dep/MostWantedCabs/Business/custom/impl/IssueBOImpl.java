package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.IssueBO;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.IssueDTO;
import lk.ijse.dep.MostWantedCabs.DTO.IssueInfoDTO;
import lk.ijse.dep.MostWantedCabs.Entity.CustomEntity;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import lk.ijse.dep.MostWantedCabs.Entity.Issue;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class IssueBOImpl implements IssueBO {

    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);
    private QuaryDAO quaryDAO = DAOFactory.getInstance().getDAO(DAOType.QUARY);
    private DriverDAO driverDAO = DAOFactory.getInstance().getDAO(DAOType.DRIVER);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    @Override
    public String getLastIssueId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lastIssueID = issueDAO.getLastIssueID();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastIssueID;

    }

    @Override
    public List<String> getAllIssueIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Issue> issues = issueDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> issueIds = new ArrayList<>();
        for (Issue issue : issues) {
            issueIds.add(issue.getId());
        }
        return issueIds;

    }

    @Override
    public void issueVehicle(IssueDTO issue) throws Exception {
        String driverId = "";
        String staues = "Not Available";

        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        issueDAO.setEntityMaanager(entityManager);
        driverDAO.setEntityMaanager(entityManager);
        vehicleDAO.setEntityMaanager(entityManager);
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();

        issueDAO.save(new Issue(issue.getId(), issue.getDate(), vehicleDAO.find(issue.getVehicleId()), customerDAO.find(issue.getCustomerId()), issue.getStatues()));

//        driverId = driverDAO.find(issue.getDriverID());


        if (!issue.getDriverID().equals("Without Driver")) {

            Driver driver = driverDAO.find(issue.getDriverID());
            issue.setDriverID(driver.getId());

            driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(),
                    driver.getLicenseNo(), driver.getSalaryPerDay(), staues));
        }

        Vehicle vehicle = vehicleDAO.find(issue.getVehicleId());
        vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(),
                staues, vehicle.getOwner()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<String> findAllNotReturnId(String status) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Issue> issuId = issueDAO.findallIssueIds(status);
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> id = new ArrayList<>();
        for (Issue issue : issuId) {
            id.add(issue.getId());
        }
        return id;

    }

    @Override
    public boolean updateIssue(IssueInfoDTO infoDTOS) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        quaryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        boolean b = quaryDAO.updateStatues(new CustomEntity(infoDTOS.getId(), infoDTOS.getStatues()));
        entityManager.getTransaction().commit();
        entityManager.close();
        return b;

    }

    @Override
    public IssueDTO findIssue(String issueId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        Issue issue = issueDAO.find(issueId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new IssueDTO(issue.getId(), issue.getDate(), issue.getVehicle().getId(), issue.getCustomer().getId(), issue.getStatues());
    }

    @Override
    public List<IssueInfoDTO> getIssueInfo() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        quaryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<CustomEntity> issueifo = quaryDAO.getIssueinfo();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<IssueInfoDTO> issueInfoDTOS = new ArrayList<>();
        for (CustomEntity customEntity : issueifo) {
            issueInfoDTOS.add(new IssueInfoDTO(customEntity.getId(), customEntity.getDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getVehicleId(), customEntity.getVehicleModel(), customEntity.getDriverStatues(), customEntity.getStatues()));
        }
        return issueInfoDTOS;

    }
}
