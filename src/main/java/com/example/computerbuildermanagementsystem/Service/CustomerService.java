package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Model.Customer;
import com.example.computerbuildermanagementsystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> get() {
        return customerRepository.findAll();
    }

    public void add(Customer customer) {
        customer.setRegistrationDate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    public boolean update(Integer id, Customer customer) {
        Customer oldCustomer = customerRepository.getCustomerById(id);
        if (oldCustomer == null) return false;

        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setBalance(customer.getBalance());
        oldCustomer.setRegistrationDate(LocalDateTime.now());

        customerRepository.save(oldCustomer);
        return true;
    }

    public boolean delete(Integer id) {
        Customer oldCustomer = customerRepository.getCustomerById(id);
        if (oldCustomer == null) return false;

        customerRepository.delete(oldCustomer);
        return true;
    }
    public Customer getCustomerById(Integer id){
        return customerRepository.getCustomerById(id);
    }
    public boolean addFunds(Integer id, double amount) {
        Customer old = customerRepository.getCustomerById(id);
        if (old == null) return false;
        old.setBalance(old.getBalance() + amount);
        customerRepository.save(old);
        return true;
    }
}
