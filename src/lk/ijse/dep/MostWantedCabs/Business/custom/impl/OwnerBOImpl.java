package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.OwnerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.OwnerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;

import lk.ijse.dep.MostWantedCabs.DTO.OwnerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class OwnerBOImpl implements OwnerBO {
    @Autowired
    private OwnerDAO ownerDAO ;
    @Autowired
    private VehicleDAO vehicleDAO ;

    @Override
    public void saveOwner(OwnerDTO owner) throws Exception {
        ownerDAO.save(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
    }

    @Override
    public void updateOwner(OwnerDTO owner) throws Exception {
        ownerDAO.update(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
    }

    @Override
    public void deleteOwner(String ownerId) throws Exception {
        if (vehicleDAO.existOwnerId(ownerId)) {
            throw new AlreadyExist("Owner already exists in vehicle, hence unable to delete !");
        }
        ownerDAO.delete(ownerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OwnerDTO> findAllOwners() throws Exception {
        List<Owner> owners = ownerDAO.findAll();

        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner : owners) {
            ownerDTOS.add(new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
        }
        return ownerDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public String getLastOwnerId() throws Exception {
       return ownerDAO.getLastOwnerID();
    }

    @Transactional(readOnly = true)
    @Override
    public OwnerDTO findOwner(String ownerId) throws Exception {
        Owner owner = ownerDAO.find(ownerId);
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllOwnerIds() throws Exception {
        List<Owner> owners = ownerDAO.findAll();
        List<String> owenerId = new ArrayList<>();
        for (Owner owner : owners) {
            owenerId.add(owner.getId());
        }
        return owenerId;
    }
}
