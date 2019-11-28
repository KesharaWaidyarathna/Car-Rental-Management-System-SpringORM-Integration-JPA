package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.CustomerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.CustomerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DB.JPAUtil;
import lk.ijse.dep.MostWantedCabs.DTO.CustomerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
        entityManager.getTransaction().commit();
        entityManager.close();


    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        issueDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        if (issueDAO.existCustomerId(customerId)) {
            throw new AlreadyExist("Customer already exists in an Issue, hence unable to delete !");
        }
        customerDAO.delete(customerId);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Customer> alcustomer = customerDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : alcustomer) {
            customerDTOS.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
        }
        return customerDTOS;

    }

    @Override
    public String getLastCustomerId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        String lasCustomerId = customerDAO.getLasCustomerId();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lasCustomerId;

    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        Customer customer = customerDAO.find(customerId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo());

    }

    @Override
    public List<String> getAllCustomerIds() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityMaanager(entityManager);
        entityManager.getTransaction().begin();
        List<Customer> customer = customerDAO.findAll();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<String> customerid = new ArrayList<>();
        for (Customer customer1 : customer) {
            customerid.add(customer1.getId());
        }
        return customerid;

    }
}
