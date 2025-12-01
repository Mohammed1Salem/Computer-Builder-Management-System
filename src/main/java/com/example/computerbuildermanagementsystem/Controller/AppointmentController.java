package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Model.Appointment;
import com.example.computerbuildermanagementsystem.Service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return !appointmentService.get().isEmpty()
                ? ResponseEntity.status(200).body(appointmentService.get())
                : ResponseEntity.status(400).body(new ApiResponse("Appointments not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Appointment appointment, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = appointmentService.add(appointment);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Appointment appointment, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String response = appointmentService.update(id, appointment);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{id}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable Integer employeeId) {
        String response = appointmentService.delete(id, employeeId);

        return response.contains("successfully")
                ? ResponseEntity.status(200).body(new ApiResponse(response))
                : ResponseEntity.status(400).body(new ApiResponse(response));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return appointmentService.getAppointmentById(id) != null
                ? ResponseEntity.status(200).body(appointmentService.getAppointmentById(id))
                : ResponseEntity.status(400).body(new ApiResponse("Appointment not found"));
    }

    @GetMapping("/get-by-employee-id/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Integer employeeId) {
        return !appointmentService.getAppointmentsByEmployeeId(employeeId).isEmpty()
                ? ResponseEntity.status(200).body(appointmentService.getAppointmentsByEmployeeId(employeeId))
                : ResponseEntity.status(400).body(new ApiResponse("Appointments not found for employee: " + employeeId));
    }

    @GetMapping("get-before-date/{date}")
    public ResponseEntity<?> getBeforeDate(@PathVariable LocalDateTime date) {
        return !appointmentService.getAppointmentsByAppointmentDateBefore(date).isEmpty()
                ? ResponseEntity.status(200).body(appointmentService.getAppointmentsByAppointmentDateBefore(date))
                : ResponseEntity.status(400).body(new ApiResponse("Appointments not found"));
    }
}
