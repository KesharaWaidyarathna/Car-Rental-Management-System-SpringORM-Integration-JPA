package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.QuaryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class VehicleBOImpl implements VehicleBO {

    @Autowired
    private VehicleDAO vehicleDAO ;
    @Autowired
    private IssueDAO issueDAO ;
    @Autowired
    private QuaryDAO quaryDAO ;

    @Override
    public void saveVehicle(VehicleDTO vehicle) throws Exception {
        vehicleDAO.save(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
    }

    @Override
    public void updateVehicle(VehicleDTO vehicle) throws Exception {
        vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
    }

    @Override
    public void deleteVehicle(String vehicleId) throws Exception {
        if (issueDAO.existVehicleId(vehicleId)) {
            throw new AlreadyExist("Vehicle already exists in an Issue, hence unable to delete !");
        }
        vehicleDAO.delete(vehicleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehicleDTO> findAllVehicles() throws Exception {
        List<Vehicle> vehicles = vehicleDAO.findAll();
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
        }
        return vehicleDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public String getLastVehicleId() throws Exception {
        String lastVehicleID = vehicleDAO.getLastVehicleID();
        return lastVehicleID;
    }

    @Transactional(readOnly = true)
    @Override
    public VehicleDTO findVehicle(String vehicleId) throws Exception {
        Vehicle vehicle = vehicleDAO.find(vehicleId);
        return new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllVehicleIds() throws Exception {
        List<Vehicle> vehicles = vehicleDAO.findAll();
        List<String> vehicleID = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleID.add(vehicle.getId());
        }
        return vehicleID;
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllAvailableVehicleIds() throws Exception {
        String staues = "Available";
        List<String> vehicles = vehicleDAO.availableVehicles(staues);
        return vehicles;
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehicleDTO> searchVehicle(String search) throws Exception {
        List<Vehicle> vehicles = quaryDAO.SearchVehicle(search);
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
        }
        return vehicleDTOS;

    }
}
