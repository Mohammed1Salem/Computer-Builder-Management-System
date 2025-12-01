package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Model.Delivery;
import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Model.Order;
import com.example.computerbuildermanagementsystem.Repository.DeliveryRepository;
import com.example.computerbuildermanagementsystem.Repository.EmployeeRepository;
import com.example.computerbuildermanagementsystem.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    public List<Delivery> get() {
        return deliveryRepository.findAll();
    }

    public String add(Delivery delivery) {
        if (orderRepository.getOrderById(delivery.getOrderId()).getStatus().equalsIgnoreCase("ASSEMBLING"))
            return "Order is still assembling";
        Employee employee = employeeRepository.getEmployeeById(delivery.getDeliveryEmployeeId());
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            return "Only DELIVERY_DRIVER can add deliveries";

        Order order = orderRepository.getOrderById(delivery.getOrderId());
        if (order == null) return "Order not found";

        if (order.getStatus().equalsIgnoreCase("DELIVERED"))
            return "Order already delivered";

        delivery.setDeliveryEmployeeId(delivery.getDeliveryEmployeeId());
        delivery.setCreatedAt(LocalDateTime.now());
        deliveryRepository.save(delivery);

        order.setStatus("OUT_FOR_DELIVERY");
        orderRepository.save(order);

        return "Delivery created successfully";
    }

    public String update(Integer deliveryId, Integer deliveryEmployeeId, Delivery delivery) {
        Delivery old = deliveryRepository.getDeliveryById(deliveryId);
        if (old == null) return "Delivery not found";

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            return "Only DELIVERY_DRIVER can update deliveries";

        old.setDeliveryDate(delivery.getDeliveryDate());
        old.setDeliveryEmployeeId(deliveryEmployeeId);
        old.setOrderId(delivery.getOrderId());

        deliveryRepository.save(old);
        return "Delivery updated successfully";
    }

    public String delete(Integer deliveryId, Integer deliveryEmployeeId) {
        Delivery old = deliveryRepository.getDeliveryById(deliveryId);
        if (old == null) return "Delivery not found";

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            return "Only DELIVERY_DRIVER can delete deliveries";

        deliveryRepository.delete(old);
        return "Delivery deleted successfully";
    }

    public String completeDelivery(Integer deliveryId, Integer deliveryEmployeeId) {
        Delivery delivery = deliveryRepository.getDeliveryById(deliveryId);
        if (delivery == null) return "Delivery not found";

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            return "Only DELIVERY_DRIVER can complete deliveries";

        if (!delivery.getDeliveryEmployeeId().equals(deliveryEmployeeId))
            return "This delivery is not assigned to you";

        delivery.setDeliveryDate(LocalDateTime.now());
        deliveryRepository.save(delivery);

        Order order = orderRepository.getOrderById(delivery.getOrderId());
        if (order != null) {
            order.setStatus("DELIVERED");
            orderRepository.save(order);
        }

        return "Delivery completed and order marked as DELIVERED successfully";
    }

    public Delivery getDeliveryById(Integer id) {
        return deliveryRepository.getDeliveryById(id);
    }

    public List<Delivery> getDeliveriesByEmployeeId(Integer deliveryEmployeeId) {
        return deliveryRepository.getDeliveriesByDeliveryEmployeeId(deliveryEmployeeId);
    }

}
