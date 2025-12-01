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
        return !orderService.get().isEmpty()
                ? ResponseEntity.status(200).body(orderService.get())
                : ResponseEntity.status(400).body(new ApiResponse("Orders not found"));
    }

    @PostMapping("/add/{customerId}/{itemId}/{itemType}")
    public ResponseEntity<?> add(@PathVariable Integer customerId, @PathVariable Integer itemId, @PathVariable String itemType) {
        String response = orderService.add(customerId, itemId, itemType);
        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{employeeId}/{orderId}/{status}")
    public ResponseEntity<?> update(@PathVariable Integer employeeId,@PathVariable Integer orderId, @PathVariable String status) {
        String response = orderService.update(employeeId,orderId, status);
        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> delete(@PathVariable Integer orderId) {
        return orderService.delete(orderId)
                ? ResponseEntity.status(200).body(new ApiResponse("Order deleted successfully"))
                : ResponseEntity.status(400).body(new ApiResponse("Order not found"));
    }

    @GetMapping("/get-orders-by-customer-id/{id}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Integer id) {
        return !orderService.getOrdersByCustomerId(id).isEmpty()
                ? ResponseEntity.status(200).body(orderService.getOrdersByCustomerId(id))
                : ResponseEntity.status(400).body(new ApiResponse("Orders not found for customer: " + id));
    }

    @GetMapping("/get-order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id) != null
                ? ResponseEntity.status(200).body(orderService.getOrderById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Order not found: " + id));
    }

    @GetMapping("get-by-status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        return orderService.getOrderByStatus(status) != null
                ? ResponseEntity.status(200).body(orderService.getOrderByStatus(status))
                : ResponseEntity.status(400).body(new ApiResponse("Order not found of status: " + status));

    }
}
