package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.ReturnBO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnDTO;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnInfoDTO;
import lk.ijse.dep.MostWantedCabs.Entity.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RetrunBOImpl implements ReturnBO {

    private QuaryDAO quaryDAO = DAOFactory.getInstance().getDAO(DAOType.QUARY);
    private ReturnDAO returnDAO = DAOFactory.getInstance().getDAO(DAOType.RETURN);
    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);
    private DriverDAO driverDAO = DAOFactory.getInstance().getDAO(DAOType.DRIVER);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);

    @Override


    public List<ReturnInfoDTO> getReturnInfo() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        quaryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();

        List<CustomReturnEntity> returnEntities = quaryDAO.getReturnInfo();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<ReturnInfoDTO> infoDTOS = new ArrayList<>();
        for (CustomReturnEntity returnEntity : returnEntities) {
            infoDTOS.add(new ReturnInfoDTO(returnEntity.getIssueDate(), returnEntity.getReturnDate(), returnEntity.getIssueId(), returnEntity.getAdditionalKilometers(),
                    returnEntity.getCostOfDamage(), returnEntity.getVehicleModel(), returnEntity.getCustomerModel(), returnEntity.getTotal()));
        }
        return infoDTOS;

    }

    @Override
    public void saveReturn(ReturnDTO returnDTO) throws Exception {
        String staues = "Available";

        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        returnDAO.setEntityMaanager(entityManager);
        issueDAO.setEntityMaanager(entityManager);
        driverDAO.setEntityMaanager(entityManager);
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();

        returnDAO.save(new Return(issueDAO.find(returnDTO.getIssueId()), returnDTO.getReturnDate(), returnDTO.getAdditionalDistance(), returnDTO.getDamageCost(), returnDTO.getTotal()));


        Issue issue = issueDAO.find(returnDTO.getIssueId());

        Vehicle vehicle = vehicleDAO.find(issue.getVehicle().getId());
        vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), staues, vehicle.getOwner()));


        String driverId = driverDAO.getDriverId(issue.getDriver().getId());
        if (!(driverId == null)) {
            Driver driver = driverDAO.find(driverId);
            driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), staues));
        }


        staues = "Returned";
        issueDAO.update(new Issue(issue.getId(), issue.getDate(), issue.getVehicle(), issue.getCustomer(), staues));
        entityManager.getTransaction().commit();
        entityManager.close();


    }
}
