package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return !employeeService.get().isEmpty()
                ? ResponseEntity.status(200).body(employeeService.get())
                : ResponseEntity.status(400).body(new ApiResponse("Employees not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        return ResponseEntity.status(200)
                .body(new ApiResponse(employeeService.add(employee)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Employee employee, Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = employeeService.update(id, employee);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return employeeService.delete(id)
                ? ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"))
                : ResponseEntity.status(400).body(new ApiResponse("Employee not found"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id) != null
                ? ResponseEntity.status(200).body(employeeService.getEmployeeById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Employee not found with id: " + id));
    }

}
