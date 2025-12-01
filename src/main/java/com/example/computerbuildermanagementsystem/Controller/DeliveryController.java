package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Delivery;
import com.example.computerbuildermanagementsystem.Service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        List<Delivery> deliveries = deliveryService.get();
        return !deliveries.isEmpty() ? ResponseEntity.status(200).body(deliveries)
                : ResponseEntity.status(400).body(new ApiResponse("Deliveries not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Delivery delivery, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = deliveryService.add(delivery);
        return response.contains("successfully") ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{deliveryId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer deliveryId, @PathVariable Integer employeeId, @RequestBody @Valid Delivery delivery, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = deliveryService.update(deliveryId, employeeId, delivery);
        return response.contains("successfully") ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{deliveryId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer deliveryId, @PathVariable Integer employeeId) {
        String response = deliveryService.delete(deliveryId, employeeId);
        return response.contains("successfully") ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return deliveryService.getDeliveryById(id) != null ? ResponseEntity.status(200).body(deliveryService.getDeliveryById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Delivery not found"));
    }

    @GetMapping("/get-by-employee-id/{id}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Integer id) {
        List<Delivery> deliveries = deliveryService.getDeliveriesByEmployeeId(id);
        return !deliveries.isEmpty() ? ResponseEntity.status(200).body(deliveries)
                : ResponseEntity.status(400).body(new ApiResponse("No deliveries found for employee: " + id));
    }

    @PutMapping("/complete/{deliveryId}/{driverEmployeeId}")
    public ResponseEntity<?> complete(@PathVariable Integer deliveryId, @PathVariable Integer driverEmployeeId) {
        String response = deliveryService.completeDelivery(deliveryId, driverEmployeeId);
        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }
}
