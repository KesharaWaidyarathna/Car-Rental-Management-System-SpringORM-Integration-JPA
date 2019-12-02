package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleCategoryBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleCategoryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class VehicleCategoryBOImpl implements VehicleCategoryBO {
    @Autowired
    private VehicleCategoryDAO vehicleCategoryDAO ;
    @Autowired
    private VehicleDAO vehicleDAO;


    @Override
    public void saveVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        vehicleCategoryDAO.save(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
    }

    @Override
    public void updateVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        vehicleCategoryDAO.update(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
    }

    @Override
    public void deleteVehicleCategory(String vehicleCategoryId) throws Exception {
        if (vehicleDAO.existVehicleCategoryId(vehicleCategoryId)) {
            throw new AlreadyExist("Vehicle Category already exists in vehicle, hence unable to delete !");
        }
        vehicleCategoryDAO.delete(vehicleCategoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehicleCategoryDTO> findAllVehicleCategories() throws Exception {
        List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
        List<VehicleCategoryDTO> vehicleCategoryDTOS = new ArrayList<>();
        for (VehicleCategory vehicleCategory : vehicleCategories) {
            vehicleCategoryDTOS.add(new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
        }
        return vehicleCategoryDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public String getLastVehicleCategoryId() throws Exception {
        String lastCategoryID = vehicleCategoryDAO.getLastCategoryID();
        return lastCategoryID;
    }

    @Transactional(readOnly = true)
    @Override
    public VehicleCategoryDTO findVehicleCategory(String vehicleCategoryId) throws Exception {
        VehicleCategory vehicleCategory = vehicleCategoryDAO.find(vehicleCategoryId);
        return new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllVehicleCategoryIds() throws Exception {
        List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
        List<String> vehicleCategoryId = new ArrayList<>();
        for (VehicleCategory vehicleCategory : vehicleCategories) {
            vehicleCategoryId.add(vehicleCategory.getId());
        }
        return vehicleCategoryId;
    }
}
