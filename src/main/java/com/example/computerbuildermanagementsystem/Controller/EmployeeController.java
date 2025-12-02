package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(employeeService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Employee employee) {
        employeeService.update(id, employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(id));
    }
}
