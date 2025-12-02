package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
import com.example.computerbuildermanagementsystem.Model.Component;
import com.example.computerbuildermanagementsystem.Model.Customer;
import com.example.computerbuildermanagementsystem.Model.Order;
import com.example.computerbuildermanagementsystem.Model.PCBuild;
import com.example.computerbuildermanagementsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ComponentRepository componentRepository;
    private final PCBuildRepository pcBuildRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public List<Order> get() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) throw new ApiException("No orders found");
        return orders;
    }

    public void add(Integer customerId, Integer itemId, String itemType) {
        String typeUpper = itemType.toUpperCase();
        if (!typeUpper.equals("PCBUILD") && !typeUpper.equals("CPU") &&
                !typeUpper.equals("RAM") && !typeUpper.equals("GPU") &&
                !typeUpper.equals("SSD")) throw new ApiException("Invalid item type");

        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found");

        double price;

        if (typeUpper.equals("PCBUILD")) {
            PCBuild pcBuild = pcBuildRepository.getPCBuildById(itemId);
            if (pcBuild == null) throw new ApiException("PCBuild not found");

            Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
            Component ram = componentRepository.getComponentById(pcBuild.getRamId());
            Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
            Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());

            if (cpu == null || cpu.getQuantity() < 1) throw new ApiException("CPU out of stock");
            if (ram == null || ram.getQuantity() < 1) throw new ApiException("RAM out of stock");
            if (gpu == null || gpu.getQuantity() < 1) throw new ApiException("GPU out of stock");
            if (ssd == null || ssd.getQuantity() < 1) throw new ApiException("SSD out of stock");

            price = pcBuild.getPrice();
            if (customer.getBalance() < price) throw new ApiException("Not enough money");

            cpu.setQuantity(cpu.getQuantity() - 1);
            ram.setQuantity(ram.getQuantity() - 1);
            gpu.setQuantity(gpu.getQuantity() - 1);
            ssd.setQuantity(ssd.getQuantity() - 1);

            componentRepository.save(cpu);
            componentRepository.save(ram);
            componentRepository.save(gpu);
            componentRepository.save(ssd);

            customer.setBalance(customer.getBalance() - price);
            customerRepository.save(customer);

            Order order = new Order();
            order.setCustomerId(customerId);
            order.setItemId(itemId);
            order.setItemType("PCBUILD");
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("ASSEMBLING");
            orderRepository.save(order);

        } else {
            Component component = componentRepository.getComponentById(itemId);
            if (component == null) throw new ApiException("Component not found");
            if (component.getQuantity() < 1) throw new ApiException("Not enough stock");

            price = component.getPrice();
            if (customer.getBalance() < price) throw new ApiException("Insufficient balance");

            component.setQuantity(component.getQuantity() - 1);
            componentRepository.save(component);

            customer.setBalance(customer.getBalance() - price);
            customerRepository.save(customer);

            Order order = new Order();
            order.setCustomerId(customerId);
            order.setItemId(itemId);
            order.setItemType(component.getType());
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("OUT_FOR_DELIVERY");
            orderRepository.save(order);
        }
    }

    public void update(Integer assemblerId, Integer orderId, String status) {
        if (!employeeRepository.getEmployeeById(assemblerId).getRole().equalsIgnoreCase("ASSEMBLER"))
            throw new ApiException("Employee is not an assembler");

        Order order = orderRepository.getOrderById(orderId);
        if (order == null) throw new ApiException("Order not found");

        if (!status.matches("ASSEMBLING|OUT_FOR_DELIVERY|DELIVERED"))
            throw new ApiException("Invalid status");

        order.setStatus(status);
        orderRepository.save(order);
    }

    public void delete(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) throw new ApiException("Order not found");

        Customer customer = customerRepository.getCustomerById(order.getCustomerId());
        if (customer == null) throw new ApiException("Customer not found");

        if (order.getItemType().equals("PCBUILD")) {
            PCBuild pcBuild = pcBuildRepository.getPCBuildById(order.getItemId());
            customer.setBalance(customer.getBalance() + pcBuild.getPrice());
            customerRepository.save(customer);

            Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
            Component ram = componentRepository.getComponentById(pcBuild.getRamId());
            Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
            Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());

            cpu.setQuantity(cpu.getQuantity() + 1);
            ram.setQuantity(ram.getQuantity() + 1);
            gpu.setQuantity(gpu.getQuantity() + 1);
            ssd.setQuantity(ssd.getQuantity() + 1);

            componentRepository.save(cpu);
            componentRepository.save(ram);
            componentRepository.save(gpu);
            componentRepository.save(ssd);

        } else {
            Component component = componentRepository.getComponentById(order.getItemId());
            customer.setBalance(customer.getBalance() + component.getPrice());
            customerRepository.save(customer);

            component.setQuantity(component.getQuantity() + 1);
            componentRepository.save(component);
        }

        orderRepository.delete(order);
    }

    public Order getOrderById(Integer id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) throw new ApiException("Order not found: " + id);
        return order;
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        List<Order> orders = orderRepository.getOrdersByCustomerId(customerId);
        if (orders.isEmpty()) throw new ApiException("Orders not found for customer: " + customerId);
        return orders;
    }

    public List<Order> getOrderByStatus(String status) {
        List<Order> orders = orderRepository.getOrderByStatus(status);
        if (orders.isEmpty()) throw new ApiException("Orders not found with status: " + status);
        return orders;
    }
}
