package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.UserDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;
import lk.ijse.dep.MostWantedCabs.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class UserBOImpl implements UserBO {

    @Autowired
    UserDAO userDAO ;

    @Override
    public UserDTO findUser(String password) throws Exception {
        User user = userDAO.find(password);
        return new UserDTO(user.getUserName(), user.getPassword(), user.getAddress(), user.getContactNo());
    }

    @Override
    public void save(UserDTO userDTO) throws Exception {
        userDAO.save(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getAddress(), userDTO.getContactNo()));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existUser(String password) throws Exception {
        return userDAO.existUser(password);
    }

    @Override
    public boolean resetPassword(String newPassword, String oldpassword) throws Exception {
        return userDAO.updatePassword(newPassword, oldpassword);
    }
}
