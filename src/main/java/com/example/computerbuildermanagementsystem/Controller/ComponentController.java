package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Component;
import com.example.computerbuildermanagementsystem.Service.ComponentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/component")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return !componentService.get().isEmpty()
                ? ResponseEntity.status(200).body(componentService.get())
                : ResponseEntity.status(400).body(new ApiResponse("Components not found"));
    }

    @PostMapping("/add/{employeeId}")
    public ResponseEntity<?> add(@PathVariable Integer employeeId, @RequestBody @Valid Component component, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = componentService.add(employeeId, component);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{componentId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer componentId, @PathVariable Integer employeeId, @RequestBody @Valid Component component, Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = componentService.update(employeeId, componentId, component);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{componentId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer componentId, @PathVariable Integer employeeId) {
        String response = componentService.delete(employeeId, componentId);
        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @GetMapping("/get-by-component-id/{id}")
    public ResponseEntity<?> getComponentById(@PathVariable Integer id) {
        return componentService.getComponentById(id) != null
                ? ResponseEntity.status(200).body(componentService.getComponentById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Component not found with id: " + id));
    }

    @GetMapping("get-by-type/{type}")
    public ResponseEntity<?> getCPUs(@PathVariable String type) {
        return !componentService.getByType(type).isEmpty()
                ? ResponseEntity.status(200).body(componentService.get())
                : ResponseEntity.status(400).body(new ApiResponse("type not found"));
    }

    @GetMapping("get-by-price-less-than/{price}")
    public ResponseEntity<?> getByPriceLessThan(@PathVariable double price) {
        return !componentService.getByPriceLessThan(price).isEmpty()
                ? ResponseEntity.status(200).body(componentService.getByPriceLessThan(price))
                : ResponseEntity.status(400).body(new ApiResponse("type not found"));
    }
}
