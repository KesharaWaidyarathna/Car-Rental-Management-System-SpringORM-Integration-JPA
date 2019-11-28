package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.QuaryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {

    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);
    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);
    private QuaryDAO quaryDAO = DAOFactory.getInstance().getDAO(DAOType.QUARY);

    @Override
    public void saveVehicle(VehicleDTO vehicle) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        vehicleDAO.save(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void updateVehicle(VehicleDTO vehicle) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void deleteVehicle(String vehicleId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        if (issueDAO.existVehicleId(vehicleId)) {
            throw new AlreadyExist("Vehicle already exists in an Issue, hence unable to delete !");
        }
        vehicleDAO.delete(vehicleId);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public List<VehicleDTO> findAllVehicles() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Vehicle> vehicles = vehicleDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
        }
        return vehicleDTOS;

    }

    @Override
    public String getLastVehicleId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lastVehicleID = vehicleDAO.getLastVehicleID();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastVehicleID;

    }

    @Override
    public VehicleDTO findVehicle(String vehicleId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        Vehicle vehicle = vehicleDAO.find(vehicleId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner());
    }

    @Override
    public List<String> getAllVehicleIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Vehicle> vehicles = vehicleDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> vehicleID = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleID.add(vehicle.getId());
        }
        return vehicleID;

    }

    @Override
    public List<String> getAllAvailableVehicleIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String staues = "Available";
        List<String> vehicles = vehicleDAO.availableVehicles(staues);
        entityManager.getTransaction().commit();
        entityManager.close();
        return vehicles;

    }

    @Override
    public List<VehicleDTO> searchVehicle(String search) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Vehicle> vehicles = quaryDAO.SearchVehicle(search);
        entityManager.getTransaction().commit();
        entityManager.close();
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
        }
        return vehicleDTOS;

    }
}
