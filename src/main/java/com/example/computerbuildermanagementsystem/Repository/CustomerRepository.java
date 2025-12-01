package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer getCustomerById(Integer id);
}
