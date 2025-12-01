package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Model.Appointment;
import com.example.computerbuildermanagementsystem.Model.Employee;
import com.example.computerbuildermanagementsystem.Repository.AppointmentRepository;
import com.example.computerbuildermanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;

    public List<Appointment> get() {
        return appointmentRepository.findAll();
    }

    public String add(Appointment appointment) {
        Employee employee = employeeRepository.getEmployeeById(appointment.getEmployeeId());
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only PC_SPECIALIST can create appointments";

        LocalDateTime startHour = appointment.getAppointmentDate().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endHour = startHour.plusHours(1);

        List<Appointment> conflicts = appointmentRepository.getAppointmentsByEmployeeIdAndAppointmentDateBetween(
                appointment.getEmployeeId(), startHour, endHour
        );
        if (!conflicts.isEmpty()) return "Appointment time conflicts with another appointment";

        appointment.setCreatedAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
        return "Appointment added successfully";
    }

    public String update(Integer id,Appointment appointment) {
        Employee employee = employeeRepository.getEmployeeById(appointment.getEmployeeId());
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only PC_SPECIALIST can update appointments";

        Appointment old = appointmentRepository.getAppointmentById(id);
        if (old == null) return "Appointment not found";

        LocalDateTime startHour = appointment.getAppointmentDate().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endHour = startHour.plusHours(1);

        List<Appointment> conflicts = appointmentRepository.getAppointmentsByEmployeeIdAndAppointmentDateBetween(
                appointment.getEmployeeId(), startHour, endHour
        );
        conflicts.removeIf(a -> a.getId().equals(id));
        if (!conflicts.isEmpty()) return "Appointment time conflicts with another appointment";

        old.setCustomerId(appointment.getCustomerId());
        old.setEmployeeId(appointment.getEmployeeId());
        old.setAppointmentDate(appointment.getAppointmentDate());

        appointmentRepository.save(old);
        return "Appointment updated successfully";
    }

    public String delete(Integer id, Integer employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) return "Employee not found";

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            return "Only PC_SPECIALIST can delete appointments";

        Appointment old = appointmentRepository.getAppointmentById(id);
        if (old == null) return "Appointment not found";

        appointmentRepository.delete(old);
        return "Appointment deleted successfully";
    }


    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.getAppointmentById(id);
    }

    public List<Appointment> getAppointmentsByEmployeeId(Integer employeeId) {
        return appointmentRepository.getAppointmentsByEmployeeId(employeeId);
    }
    public List<Appointment> getAppointmentsByAppointmentDateBefore(LocalDateTime localDateTime){
        return appointmentRepository.getAppointmentsByAppointmentDateBefore(localDateTime);
    }
}
