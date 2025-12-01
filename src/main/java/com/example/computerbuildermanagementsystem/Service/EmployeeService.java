package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> get() {
        return employeeRepository.findAll();
    }

    public String add(Employee employee) {
        employee.setHiredDate(LocalDateTime.now());
        employeeRepository.save(employee);
        return "Employee added successfully";
    }

    public String update(Integer id, Employee employee) {
        Employee old = employeeRepository.getEmployeeById(id);
        if (old == null) return "Employee id not found";

        old.setName(employee.getName());
        old.setPassword(employee.getPassword());
        old.setEmail(employee.getEmail());
        old.setRole(employee.getRole());
        old.setHiredDate(LocalDateTime.now());

        employeeRepository.save(old);
        return "Employee updated successfully";
    }

    public boolean delete(Integer id) {
        Employee old = employeeRepository.getEmployeeById(id);
        if (old == null) return false;

        employeeRepository.delete(old);
        return true;
    }
    public Employee getEmployeeById(Integer id){
        return employeeRepository.getEmployeeById(id);
    }
}
