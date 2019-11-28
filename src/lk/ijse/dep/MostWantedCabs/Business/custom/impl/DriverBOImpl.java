package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.DriverBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.DriverDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.DriverDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DriverBOImpl implements DriverBO {
    private DriverDAO driverDAO = DAOFactory.getInstance().getDAO(DAOType.DRIVER);


    @Override
    public void saveDriver(DriverDTO driver) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        driverDAO.save(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
        entityManager.close();
        entityManager.getTransaction().commit();

    }

    @Override
    public void updateDriver(DriverDTO driver) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteDriver(String driverId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        if (driverDAO.existVehicleId(driverId)) {
            throw new AlreadyExist("Deiver already exists in an Issue detail, hence unable to delete !");
        }
        driverDAO.delete(driverId);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<DriverDTO> findAllDrivers() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Driver> drivers = driverDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver driver : drivers) {
            driverDTOS.add(new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
        }
        return driverDTOS;

    }

    @Override
    public String getLastDriverId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lastDriverID = driverDAO.getLastDriverID();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastDriverID;

    }

    @Override
    public DriverDTO findDriver(String driverId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        Driver driver = driverDAO.find(driverId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues());

    }

    @Override
    public List<String> getAllDriverIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Driver> drivers = driverDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> driverIds = new ArrayList<>();
        for (Driver driver : drivers) {
            driverIds.add(driver.getId());
        }
        return driverIds;

    }

    @Override
    public List<String> getAllAvailableDrivers() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        driverDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String staues = "Available";
        List<String> drivers = driverDAO.availableDrivers(staues);
        entityManager.getTransaction().commit();
        entityManager.close();
        return drivers;
    }

}
