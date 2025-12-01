package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.PCBuild;
import com.example.computerbuildermanagementsystem.Service.PCBuildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pcbuild")
@RequiredArgsConstructor
public class PCBuildController {

    private final PCBuildService pcBuildService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return !pcBuildService.get().isEmpty()
                ? ResponseEntity.status(200).body(pcBuildService.get())
                : ResponseEntity.status(400).body(new ApiResponse("PCBuilds not found"));
    }

    @PostMapping("/add/{employeeId}")
    public ResponseEntity<?> add(@PathVariable Integer employeeId, @RequestBody @Valid PCBuild pcBuild, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = pcBuildService.add(employeeId, pcBuild);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{pcBuildId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer pcBuildId, @PathVariable Integer employeeId, @RequestBody @Valid PCBuild pcBuild, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = pcBuildService.update(employeeId, pcBuildId, pcBuild);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{pcBuildId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer pcBuildId, @PathVariable Integer employeeId) {
        String response = pcBuildService.delete(employeeId, pcBuildId);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getPCBuildById(@PathVariable Integer id) {
        return pcBuildService.getPCBuildById(id) != null
                ? ResponseEntity.status(200).body(pcBuildService.getPCBuildById(id))
                : ResponseEntity.status(400).body(new ApiResponse("PCBuild not found with id: " + id));
    }

}
