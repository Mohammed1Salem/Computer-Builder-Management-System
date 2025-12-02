package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Delivery;
import com.example.computerbuildermanagementsystem.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(deliveryService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Delivery delivery) {
        deliveryService.add(delivery);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery added"));
    }

    @PutMapping("/update/{deliveryId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer deliveryId, @PathVariable Integer employeeId,
                                    @RequestBody Delivery delivery) {
        deliveryService.update(deliveryId, employeeId, delivery);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery updated"));
    }

    @DeleteMapping("/delete/{deliveryId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer deliveryId, @PathVariable Integer employeeId) {
        deliveryService.delete(deliveryId, employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery deleted"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(deliveryService.getDeliveryById(id));
    }

    @GetMapping("/get-by-employee-id/{id}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(deliveryService.getDeliveriesByEmployeeId(id));
    }

    @PutMapping("/complete/{deliveryId}/{driverEmployeeId}")
    public ResponseEntity<?> complete(@PathVariable Integer deliveryId, @PathVariable Integer driverEmployeeId) {
        deliveryService.completeDelivery(deliveryId, driverEmployeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery completed and order marked as DELIVERED"));
    }
}
