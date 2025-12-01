package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    Appointment getAppointmentById(Integer id);

    List<Appointment> getAppointmentsByEmployeeId(Integer employeeId);

    List<Appointment> getAppointmentsByEmployeeIdAndAppointmentDateBetween(Integer employeeId, LocalDateTime start, LocalDateTime end);

    List<Appointment> getAppointmentsByAppointmentDateBefore(LocalDateTime appointmentDateBefore);
}
