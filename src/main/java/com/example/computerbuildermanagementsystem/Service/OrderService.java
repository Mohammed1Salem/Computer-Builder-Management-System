package com.example.computerbuildermanagementsystem.Service;

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
        return orderRepository.findAll();
    }

    public String add(Integer customerId, Integer itemId, String itemType) {

        String typeUpper = itemType.toUpperCase();
        if (!typeUpper.equals("PCBUILD") && !typeUpper.equals("CPU") &&
                !typeUpper.equals("RAM") && !typeUpper.equals("GPU") &&
                !typeUpper.equals("SSD")) {
            return "Invalid item type";
        }

        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) return "Customer not found";

        double price;

        if (typeUpper.equals("PCBUILD")) {
            PCBuild pcBuild = pcBuildRepository.getPCBuildById(itemId);
            if (pcBuild == null) return "PCBuild not found";

            Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
            Component ram = componentRepository.getComponentById(pcBuild.getRamId());
            Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
            Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());

            if (cpu == null || cpu.getQuantity() < 1) return "CPU out of stock";
            if (ram == null || ram.getQuantity() < 1) return "RAM out of stock";
            if (gpu == null || gpu.getQuantity() < 1) return "GPU out of stock";
            if (ssd == null || ssd.getQuantity() < 1) return "SSD out of stock";

            price = pcBuild.getPrice();

            if (customer.getBalance() < price) return "not enough money";

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

            return "PCBuild order placed successfully";

        } else {
            Component component = componentRepository.getComponentById(itemId);
            if (component == null) return "Component not found";
            if (component.getQuantity() < 1) return "Not enough stock";

            price = component.getPrice();
            if (customer.getBalance() < price) return "Insufficient balance";

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

            return "Component order placed successfully";
        }
    }


    public String update(Integer assemblerId,Integer orderId, String status) {
        if (!employeeRepository.getEmployeeById(assemblerId).getRole().equalsIgnoreCase("assembler"))
            return "Employee is not assembler";

        Order order = orderRepository.getOrderById(orderId);
        if (order == null) return "Order not found";

        if (!status.matches("ASSEMBLING|OUT_FOR_DELIVERY|DELIVERED"))
            return "Invalid status";

        order.setStatus(status);
        orderRepository.save(order);
        return "Order updated successfully";
    }

    public boolean delete(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);

        Customer customer = customerRepository.getCustomerById(order.getCustomerId());

        if (order.getItemType().equals("PCBUILD")) {

            PCBuild pcBuild = pcBuildRepository.getPCBuildById(order.getItemId());

            customer.setBalance(customer.getBalance() + pcBuild.getPrice());
            customerRepository.save(customer);

            Component cpu = componentRepository.getComponentById(pcBuild.getCpuId());
            Component ram = componentRepository.getComponentById(pcBuild.getRamId());
            Component gpu = componentRepository.getComponentById(pcBuild.getGpuId());
            Component ssd = componentRepository.getComponentById(pcBuild.getSsdId());

            cpu.setQuantity(cpu.getQuantity() + 1);
            componentRepository.save(cpu);
            ram.setQuantity(ram.getQuantity() + 1);
            componentRepository.save(ram);
            gpu.setQuantity(gpu.getQuantity() + 1);
            componentRepository.save(gpu);
            ssd.setQuantity(ssd.getQuantity() + 1);
            componentRepository.save(ssd);

        } else {

            Component component = componentRepository.getComponentById(order.getItemId());

            customer.setBalance(customer.getBalance() + component.getPrice());
            customerRepository.save(customer);

            component.setQuantity(component.getQuantity() + 1);
            componentRepository.save(component);
        }

        orderRepository.delete(order);
        return true;
    }


    public Order getOrderById(Integer id) {
        return orderRepository.getOrderById(id);
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    public List<Order> getOrderByStatus(String status){
        return orderRepository.getOrderByStatus(status);
    }

}
