package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.UserDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;
import lk.ijse.dep.MostWantedCabs.Entity.User;

import javax.persistence.EntityManager;


public class UserBOImpl implements UserBO {

    UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOType.USER);

    @Override
    public UserDTO findUser(String password) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        User user = userDAO.find(password);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new UserDTO(user.getUserName(), user.getPassword(), user.getAddress(), user.getContactNo());
    }

    @Override
    public void save(UserDTO userDTO) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        userDAO.save(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getAddress(), userDTO.getContactNo()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public boolean existUser(String password) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        boolean b = userDAO.existUser(password);
        entityManager.close();
        entityManager.getTransaction().commit();
        return b;

    }

    @Override
    public boolean resetPassword(String newPassword, String oldpassword) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        boolean b = userDAO.updatePassword(newPassword, oldpassword);
        entityManager.getTransaction().commit();
        entityManager.close();
        return b;

    }
}
