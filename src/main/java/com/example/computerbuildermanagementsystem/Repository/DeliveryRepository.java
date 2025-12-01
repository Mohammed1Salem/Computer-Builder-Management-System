package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Delivery getDeliveryById(Integer id);
    List<Delivery> getDeliveriesByDeliveryEmployeeId(Integer deliveryEmployeeId);
    Delivery getDeliveryByOrderId(Integer orderId);
}
