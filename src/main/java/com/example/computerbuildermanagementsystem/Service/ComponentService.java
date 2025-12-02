package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
import com.example.computerbuildermanagementsystem.Model.Component;
import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Repository.ComponentRepository;
import com.example.computerbuildermanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final EmployeeRepository employeeRepository;

    public List<Component> get() {
        List<Component> components = componentRepository.findAll();
        if (components.isEmpty()) throw new ApiException("No components found");
        return components;
    }

    public void add(Integer employeeId, Component component) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) throw new ApiException("Employee not found");

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            throw new ApiException("Only ASSEMBLER or PC_SPECIALIST can add components");

        componentRepository.save(component);
    }

    public void update(Integer employeeId, Integer componentId, Component component) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) throw new ApiException("Employee not found");

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            throw new ApiException("Only ASSEMBLER or PC_SPECIALIST can update components");

        Component old = componentRepository.getComponentById(componentId);
        if (old == null) throw new ApiException("Component not found");

        old.setType(component.getType());
        old.setBrand(component.getBrand());
        old.setModel(component.getModel());
        old.setPrice(component.getPrice());
        old.setQuantity(component.getQuantity());

        componentRepository.save(old);
    }

    public void delete(Integer employeeId, Integer componentId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) throw new ApiException("Employee not found");

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            throw new ApiException("Only ASSEMBLER or PC_SPECIALIST can delete components");

        Component component = componentRepository.getComponentById(componentId);
        if (component == null) throw new ApiException("Component not found");

        componentRepository.delete(component);
    }

    public Component getComponentById(Integer id) {
        Component component = componentRepository.getComponentById(id);
        if (component == null) throw new ApiException("Component not found");
        return component;
    }

    public List<Component> getByType(String type) {
        List<Component> components = componentRepository.getByType(type);
        if (components.isEmpty()) throw new ApiException("No components with type " + type + " found");
        return components;
    }

    public List<Component> getByPriceLessThan(double price) {
        List<Component> components = componentRepository.getByPriceLessThan(price);
        if (components.isEmpty()) throw new ApiException("No components found with price less than " + price);
        return components;
    }
}
