package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
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
        List<Delivery> deliveries = deliveryRepository.findAll();
        if (deliveries.isEmpty()) throw new ApiException("No deliveries found");
        return deliveries;
    }

    public void add(Delivery delivery) {
        Order order = orderRepository.getOrderById(delivery.getOrderId());
        if (order == null) throw new ApiException("Order not found");
        if (order.getStatus().equalsIgnoreCase("ASSEMBLING"))
            throw new ApiException("Order is still assembling");
        if (order.getStatus().equalsIgnoreCase("DELIVERED"))
            throw new ApiException("Order already delivered");

        Employee employee = employeeRepository.getEmployeeById(delivery.getDeliveryEmployeeId());
        if (employee == null) throw new ApiException("Employee not found");
        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            throw new ApiException("Only DELIVERY_DRIVER can add deliveries");

        delivery.setCreatedAt(LocalDateTime.now());
        deliveryRepository.save(delivery);

        order.setStatus("OUT_FOR_DELIVERY");
        orderRepository.save(order);
    }

    public void update(Integer deliveryId, Integer deliveryEmployeeId, Delivery delivery) {
        Delivery old = deliveryRepository.getDeliveryById(deliveryId);
        if (old == null) throw new ApiException("Delivery not found");

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) throw new ApiException("Employee not found");
        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            throw new ApiException("Only DELIVERY_DRIVER can update deliveries");

        old.setDeliveryDate(delivery.getDeliveryDate());
        old.setDeliveryEmployeeId(deliveryEmployeeId);
        old.setOrderId(delivery.getOrderId());

        deliveryRepository.save(old);
    }

    public void delete(Integer deliveryId, Integer deliveryEmployeeId) {
        Delivery old = deliveryRepository.getDeliveryById(deliveryId);
        if (old == null) throw new ApiException("Delivery not found");

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) throw new ApiException("Employee not found");
        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            throw new ApiException("Only DELIVERY_DRIVER can delete deliveries");

        deliveryRepository.delete(old);
    }

    public void completeDelivery(Integer deliveryId, Integer deliveryEmployeeId) {
        Delivery delivery = deliveryRepository.getDeliveryById(deliveryId);
        if (delivery == null) throw new ApiException("Delivery not found");

        Employee employee = employeeRepository.getEmployeeById(deliveryEmployeeId);
        if (employee == null) throw new ApiException("Employee not found");
        if (!employee.getRole().equalsIgnoreCase("DELIVERY_DRIVER"))
            throw new ApiException("Only DELIVERY_DRIVER can complete deliveries");
        if (!delivery.getDeliveryEmployeeId().equals(deliveryEmployeeId))
            throw new ApiException("This delivery is not assigned to you");

        delivery.setDeliveryDate(LocalDateTime.now());
        deliveryRepository.save(delivery);

        Order order = orderRepository.getOrderById(delivery.getOrderId());
        if (order != null) {
            order.setStatus("DELIVERED");
            orderRepository.save(order);
        }
    }

    public Delivery getDeliveryById(Integer id) {
        Delivery delivery = deliveryRepository.getDeliveryById(id);
        if (delivery == null) throw new ApiException("Delivery not found");
        return delivery;
    }

    public List<Delivery> getDeliveriesByEmployeeId(Integer deliveryEmployeeId) {
        List<Delivery> deliveries = deliveryRepository.getDeliveriesByDeliveryEmployeeId(deliveryEmployeeId);
        if (deliveries.isEmpty()) throw new ApiException("No deliveries found for employee: " + deliveryEmployeeId);
        return deliveries;
    }
}
