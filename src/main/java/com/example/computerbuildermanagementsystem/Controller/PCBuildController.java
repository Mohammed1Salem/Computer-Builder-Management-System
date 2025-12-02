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
        return ResponseEntity.status(200).body(pcBuildService.get());
    }

    @PostMapping("/add/{employeeId}")
    public ResponseEntity<?> add(@PathVariable Integer employeeId, @RequestBody @Valid PCBuild pcBuild) {
        pcBuildService.add(employeeId, pcBuild);
        return ResponseEntity.status(200).body(new ApiResponse("PCBuild added successfully"));
    }

    @PutMapping("/update/{pcBuildId}/{employeeId}")
    public ResponseEntity<?> update(@PathVariable Integer pcBuildId, @PathVariable Integer employeeId, @RequestBody @Valid PCBuild pcBuild) {
        pcBuildService.update(employeeId, pcBuildId, pcBuild);
        return ResponseEntity.status(200).body(new ApiResponse("PCBuild updated successfully"));
    }

    @DeleteMapping("/delete/{pcBuildId}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer pcBuildId, @PathVariable Integer employeeId) {
        pcBuildService.delete(employeeId, pcBuildId);
        return ResponseEntity.status(200).body(new ApiResponse("PCBuild deleted successfully"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getPCBuildById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(pcBuildService.getPCBuildById(id));
    }
}
