package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.DriverBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.DriverDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.DriverDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class DriverBOImpl implements DriverBO {
    @Autowired
    private DriverDAO driverDAO ;


    @Override
    public void saveDriver(DriverDTO driver) throws Exception {
        driverDAO.save(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
    }

    @Override
    public void updateDriver(DriverDTO driver) throws Exception {
        driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
    }

    @Override
    public void deleteDriver(String driverId) throws Exception {
        if (driverDAO.existVehicleId(driverId)) {
            throw new AlreadyExist("Deiver already exists in an Issue detail, hence unable to delete !");
        }
        driverDAO.delete(driverId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DriverDTO> findAllDrivers() throws Exception {
        List<Driver> drivers = driverDAO.findAll();
        List<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver driver : drivers) {
            driverDTOS.add(new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
        }
        return driverDTOS;

    }

    @Transactional(readOnly = true)
    @Override
    public String getLastDriverId() throws Exception {
        return driverDAO.getLastDriverID();
    }

    @Transactional(readOnly = true)
    @Override
    public DriverDTO findDriver(String driverId) throws Exception {
        Driver driver = driverDAO.find(driverId);
        return new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllDriverIds() throws Exception {
        List<Driver> drivers = driverDAO.findAll();
        List<String> driverIds = new ArrayList<>();
        for (Driver driver : drivers) {
            driverIds.add(driver.getId());
        }
        return driverIds;
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllAvailableDrivers() throws Exception {
        String staues = "Available";
        List<String> drivers = driverDAO.availableDrivers(staues);
        return drivers;
    }

}
