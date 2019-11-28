package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.OwnerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.OwnerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.OwnerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class OwnerBOImpl implements OwnerBO {
    private OwnerDAO ownerDAO = DAOFactory.getInstance().getDAO(DAOType.OWNER);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);

    @Override
    public void saveOwner(OwnerDTO owner) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        ownerDAO.save(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void updateOwner(OwnerDTO owner) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        ownerDAO.update(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteOwner(String ownerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        vehicleDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        if (vehicleDAO.existOwnerId(ownerId)) {
            throw new AlreadyExist("Owner already exists in vehicle, hence unable to delete !");
        }
        ownerDAO.delete(ownerId);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public List<OwnerDTO> findAllOwners() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Owner> owners = ownerDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner : owners) {
            ownerDTOS.add(new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
        }
        return ownerDTOS;

    }

    @Override
    public String getLastOwnerId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lastOwnerID = ownerDAO.getLastOwnerID();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastOwnerID;

    }

    @Override
    public OwnerDTO findOwner(String ownerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        Owner owner = ownerDAO.find(ownerId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress());

    }

    @Override
    public List<String> getAllOwnerIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        ownerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Owner> owners = ownerDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> owenerId = new ArrayList<>();
        for (Owner owner : owners) {
            owenerId.add(owner.getId());
        }
        return owenerId;

    }
}
