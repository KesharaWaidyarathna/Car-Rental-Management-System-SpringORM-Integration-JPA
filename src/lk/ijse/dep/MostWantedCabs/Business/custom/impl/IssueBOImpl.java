package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.IssueBO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.IssueDTO;
import lk.ijse.dep.MostWantedCabs.DTO.IssueInfoDTO;
import lk.ijse.dep.MostWantedCabs.Entity.CustomEntity;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import lk.ijse.dep.MostWantedCabs.Entity.Issue;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class IssueBOImpl implements IssueBO {

    @Autowired
    private IssueDAO issueDAO;
    @Autowired
    private QuaryDAO quaryDAO ;
    @Autowired
    private DriverDAO driverDAO ;
    @Autowired
    private VehicleDAO vehicleDAO ;
    @Autowired
    private CustomerDAO customerDAO ;

    @Transactional(readOnly = true)
    @Override
    public String getLastIssueId() throws Exception {
        return issueDAO.getLastIssueID();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllIssueIds() throws Exception {
        List<Issue> issues = issueDAO.findAll();
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
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> findAllNotReturnId(String status) throws Exception {
        List<Issue> issuId = issueDAO.findallIssueIds(status);
        List<String> id = new ArrayList<>();
        for (Issue issue : issuId) {
            id.add(issue.getId());
        }
        return id;

    }


    @Override
    public boolean updateIssue(IssueInfoDTO infoDTOS) throws Exception {
        return quaryDAO.updateStatues(new CustomEntity(infoDTOS.getId(), infoDTOS.getStatues()));
    }

    @Transactional(readOnly = true)
    @Override
    public IssueDTO findIssue(String issueId) throws Exception {
        Issue issue = issueDAO.find(issueId);
        return new IssueDTO(issue.getId(), issue.getDate(), issue.getVehicle().getId(), issue.getCustomer().getId(), issue.getStatues());
    }

    @Transactional(readOnly = true)
    @Override
    public List<IssueInfoDTO> getIssueInfo() throws Exception {
        List<CustomEntity> issueifo = quaryDAO.getIssueinfo();
        List<IssueInfoDTO> issueInfoDTOS = new ArrayList<>();
        for (CustomEntity customEntity : issueifo) {
            issueInfoDTOS.add(new IssueInfoDTO(customEntity.getId(), customEntity.getDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getVehicleId(), customEntity.getVehicleModel(), customEntity.getDriverStatues(), customEntity.getStatues()));
        }
        return issueInfoDTOS;
    }
}
