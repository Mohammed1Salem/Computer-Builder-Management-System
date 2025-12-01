package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Customer;
import com.example.computerbuildermanagementsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return !customerService.get().isEmpty()
                ? ResponseEntity.status(200).body(customerService.get())
                : ResponseEntity.status(400).body(new ApiResponse("Customers not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Customer customer, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        customerService.add(customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Customer customer, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        return customerService.update(id, customer)
                ? ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully"))
                : ResponseEntity.status(400).body(new ApiResponse("Customer not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return customerService.delete(id)
                ? ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"))
                : ResponseEntity.status(400).body(new ApiResponse("Customer not found"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id) != null
                ? ResponseEntity.status(200).body(customerService.getCustomerById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Customer not found with id: " + id));
    }

    @PutMapping("add-funds/{id}/{funds}")
    public ResponseEntity<?> put(@PathVariable Integer id, @PathVariable double funds) {
        return customerService.addFunds(id,funds)
                ? ResponseEntity.status(200).body("Funds("+funds+") added to user with id: "+id)
                : ResponseEntity.status(400).body(new ApiResponse("Customer not found with id: " + id));
    }

}
