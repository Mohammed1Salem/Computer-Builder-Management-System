package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Customer;
import com.example.computerbuildermanagementsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(customerService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Customer customer) {
        customerService.add(customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Customer customer) {
        customerService.update(id, customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(customerService.getCustomerById(id));
    }

    @PutMapping("/add-funds/{id}/{funds}")
    public ResponseEntity<?> addFunds(@PathVariable Integer id, @PathVariable double funds) {
        customerService.addFunds(id, funds);
        return ResponseEntity.status(200).body(new ApiResponse("Funds(" + funds + ") added to customer with id: " + id));
    }
}
