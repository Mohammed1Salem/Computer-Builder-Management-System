package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
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
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) throw new ApiException("No customers found");
        return customers;
    }

    public void add(Customer customer) {
        customer.setRegistrationDate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    public void update(Integer id, Customer customer) {
        Customer oldCustomer = customerRepository.getCustomerById(id);
        if (oldCustomer == null) throw new ApiException("Customer not found");

        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setBalance(customer.getBalance());
        oldCustomer.setRegistrationDate(LocalDateTime.now());

        customerRepository.save(oldCustomer);
    }

    public void delete(Integer id) {
        Customer oldCustomer = customerRepository.getCustomerById(id);
        if (oldCustomer == null) throw new ApiException("Customer not found");

        customerRepository.delete(oldCustomer);
    }

    public Customer getCustomerById(Integer id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) throw new ApiException("Customer not found");
        return customer;
    }

    public void addFunds(Integer id, double amount) {
        if (amount <= 0) throw new ApiException("Amount must be greater than 0");
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) throw new ApiException("Customer not found");

        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
    }
}
