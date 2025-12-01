package com.example.computerbuildermanagementsystem.Service;

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
        return componentRepository.findAll();
    }

    public String add(Integer employeeId, Component component) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only ASSEMBLER or PC_SPECIALIST can add components";

        componentRepository.save(component);
        return "Component added successfully";
    }

    public String update(Integer employeeId, Integer componentId, Component component) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only ASSEMBLER or PC_SPECIALIST can update components";

        Component old = componentRepository.getComponentById(componentId);
        if (old == null) return "Component not found";

        old.setType(component.getType());
        old.setBrand(component.getBrand());
        old.setModel(component.getModel());
        old.setPrice(component.getPrice());
        old.setQuantity(component.getQuantity());

        componentRepository.save(old);
        return "Component updated successfully";
    }

    public String delete(Integer employeeId, Integer componentId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("ASSEMBLER") &&
                !employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only ASSEMBLER or PC_SPECIALIST can delete components";

        Component component = componentRepository.getComponentById(componentId);
        if (component == null) return "Component not found";

        componentRepository.delete(component);
        return "Component deleted successfully";
    }
    public Component getComponentById(Integer id) {
        return componentRepository.getComponentById(id);
    }

    public List<Component> getByType(String type){return componentRepository.getByType(type); }

    public List<Component> getByPriceLessThan(double price){ return componentRepository.getByPriceLessThan(price);}
}
