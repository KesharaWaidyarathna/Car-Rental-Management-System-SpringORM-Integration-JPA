package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.ReturnBO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnDTO;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnInfoDTO;
import lk.ijse.dep.MostWantedCabs.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class RetrunBOImpl implements ReturnBO {

    @Autowired
    private QuaryDAO quaryDAO;
    @Autowired
    private ReturnDAO returnDAO;
    @Autowired
    private IssueDAO issueDAO;
    @Autowired
    private DriverDAO driverDAO;
    @Autowired
    private VehicleDAO vehicleDAO;


    @Override
    public List<ReturnInfoDTO> getReturnInfo() throws Exception {
        List<CustomReturnEntity> returnEntities = quaryDAO.getReturnInfo();
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
    }
}
