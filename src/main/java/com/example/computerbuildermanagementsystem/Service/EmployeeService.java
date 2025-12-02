package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
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
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) throw new ApiException("No employees found");
        return employees;
    }

    public void add(Employee employee) {
        employee.setHiredDate(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    public void update(Integer id, Employee employee) {
        Employee old = employeeRepository.getEmployeeById(id);
        if (old == null) throw new ApiException("Employee id not found");

        old.setName(employee.getName());
        old.setPassword(employee.getPassword());
        old.setEmail(employee.getEmail());
        old.setRole(employee.getRole());
        old.setHiredDate(LocalDateTime.now());

        employeeRepository.save(old);
    }

    public void delete(Integer id) {
        Employee old = employeeRepository.getEmployeeById(id);
        if (old == null) throw new ApiException("Employee not found");

        employeeRepository.delete(old);
    }

    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) throw new ApiException("Employee not found with id: " + id);
        return employee;
    }
}
