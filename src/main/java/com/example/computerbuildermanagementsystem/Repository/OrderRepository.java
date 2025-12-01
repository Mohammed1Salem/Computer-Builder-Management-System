package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByCustomerId(Integer customerId);
    Order getOrderById(Integer id);

    List<Order> getOrderByStatus(String status);
}
