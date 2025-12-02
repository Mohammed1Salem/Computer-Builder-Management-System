package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Order;
import com.example.computerbuildermanagementsystem.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(orderService.get());
    }

    @PostMapping("/add/{customerId}/{itemId}/{itemType}")
    public ResponseEntity<?> add(@PathVariable Integer customerId, @PathVariable Integer itemId, @PathVariable String itemType) {
        orderService.add(customerId, itemId, itemType);
        return ResponseEntity.status(200).body(new ApiResponse("Order placed successfully"));
    }

    @PutMapping("/update/{employeeId}/{orderId}/{status}")
    public ResponseEntity<?> update(@PathVariable Integer employeeId, @PathVariable Integer orderId, @PathVariable String status) {
        orderService.update(employeeId, orderId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Order updated successfully"));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> delete(@PathVariable Integer orderId) {
        orderService.delete(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Order deleted successfully"));
    }

    @GetMapping("/get-orders-by-customer-id/{id}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(orderService.getOrdersByCustomerId(id));
    }

    @GetMapping("/get-order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(orderService.getOrderById(id));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(orderService.getOrderByStatus(status));
    }
}
