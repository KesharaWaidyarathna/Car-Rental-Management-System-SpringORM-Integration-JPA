package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleCategoryBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleCategoryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryBOImpl implements VehicleCategoryBO {
    private VehicleCategoryDAO vehicleCategoryDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE_CATEGORY);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);


    @Override
    public void saveVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        vehicleCategoryDAO.save(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void updateVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        vehicleCategoryDAO.update(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void deleteVehicleCategory(String vehicleCategoryId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleDAO.setEntityMaanager(entityManager);
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        if (vehicleDAO.existVehicleCategoryId(vehicleCategoryId)) {
            throw new AlreadyExist("Vehicle Category already exists in vehicle, hence unable to delete !");
        }
        vehicleCategoryDAO.delete(vehicleCategoryId);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public List<VehicleCategoryDTO> findAllVehicleCategories() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<VehicleCategoryDTO> vehicleCategoryDTOS = new ArrayList<>();
        for (VehicleCategory vehicleCategory : vehicleCategories) {
            vehicleCategoryDTOS.add(new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
        }
        return vehicleCategoryDTOS;

    }

    @Override
    public String getLastVehicleCategoryId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lastCategoryID = vehicleCategoryDAO.getLastCategoryID();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastCategoryID;

    }

    @Override
    public VehicleCategoryDTO findVehicleCategory(String vehicleCategoryId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        VehicleCategory vehicleCategory = vehicleCategoryDAO.find(vehicleCategoryId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay());

    }

    @Override
    public List<String> getAllVehicleCategoryIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        vehicleCategoryDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> vehicleCategoryId = new ArrayList<>();
        for (VehicleCategory vehicleCategory : vehicleCategories) {
            vehicleCategoryId.add(vehicleCategory.getId());
        }
        return vehicleCategoryId;
    }

}
