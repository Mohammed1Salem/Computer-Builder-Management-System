package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Component;
import com.example.computerbuildermanagementsystem.Service.ComponentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/component")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(componentService.get());
    }

    @PostMapping("/add/{employeeId}")
    public ResponseEntity<?> add(@PathVariable Integer employeeId, @RequestBody @Valid Component component) {
        componentService.add(employeeId, component);
        return ResponseEntity.status(200).body(new ApiResponse("Component added"));
    }

    @PutMapping("/update/{componentId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer componentId, @PathVariable Integer employeeId, @RequestBody @Valid Component component) {
        componentService.update(employeeId, componentId, component);
        return ResponseEntity.status(200).body(new ApiResponse("Component updated"));
    }

    @DeleteMapping("/delete/{componentId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer componentId, @PathVariable Integer employeeId) {
        componentService.delete(employeeId, componentId);
        return ResponseEntity.status(200).body(new ApiResponse("Component deleted"));
    }

    @GetMapping("/get-by-component-id/{id}")
    public ResponseEntity<?> getComponentById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(componentService.getComponentById(id));
    }

    @GetMapping("get-by-type/{type}")
    public ResponseEntity<?> getByType(@PathVariable String type) {
        return ResponseEntity.status(200).body(componentService.getByType(type));
    }

    @GetMapping("get-by-price-less-than/{price}")
    public ResponseEntity<?> getByPriceLessThan(@PathVariable double price) {
        return ResponseEntity.status(200).body(componentService.getByPriceLessThan(price));
    }
}
