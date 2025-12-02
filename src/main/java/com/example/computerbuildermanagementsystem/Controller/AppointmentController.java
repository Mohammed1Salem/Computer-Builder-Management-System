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
        return ResponseEntity.status(200).body(appointmentService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Appointment appointment) {
        appointmentService.add(appointment);
        return ResponseEntity.status(200).body(new ApiResponse("Appointment added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Appointment appointment) {

        appointmentService.update(id, appointment);
        return ResponseEntity.status(200).body(new ApiResponse("Appointment updated"));
    }

    @DeleteMapping("/delete/{id}/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable Integer employeeId) {
        appointmentService.delete(id, employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Appointment deleted"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/get-by-employee-id/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Integer employeeId) {
        return ResponseEntity.status(200).body(appointmentService.getAppointmentsByEmployeeId(employeeId));
    }

    @GetMapping("get-before-date/{date}")
    public ResponseEntity<?> getBeforeDate(@PathVariable LocalDateTime date) {
        return ResponseEntity.status(200).body(appointmentService.getAppointmentsByAppointmentDateBefore(date));
    }
}
