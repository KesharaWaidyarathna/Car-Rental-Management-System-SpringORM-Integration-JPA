package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.CustomerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.custom.CustomerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DTO.CustomerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO ;
    @Autowired
    private IssueDAO issueDAO ;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        if (issueDAO.existCustomerId(customerId)) {
            throw new AlreadyExist("Customer already exists in an Issue, hence unable to delete !");
        }
        customerDAO.delete(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        List<Customer> alcustomer = customerDAO.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : alcustomer) {
            customerDTOS.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
        }
        return customerDTOS;

    }

    @Transactional(readOnly = true)
    @Override
    public String getLastCustomerId() throws Exception {
        return  customerDAO.getLasCustomerId();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        Customer customer = customerDAO.find(customerId);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllCustomerIds() throws Exception {
        List<Customer> customer = customerDAO.findAll();
        List<String> customerid = new ArrayList<>();
        for (Customer customer1 : customer) {
            customerid.add(customer1.getId());
        }
        return customerid;

    }
}
