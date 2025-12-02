package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Api.ApiException;
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
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {throw new ApiException("No appointment found");}
        return appointments;
    }

    public void add(Appointment appointment) {
        Employee employee = employeeRepository.getEmployeeById(appointment.getEmployeeId());
        if (employee == null) throw new ApiException( "Employee not found");

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            throw new ApiException( "Only PC_SPECIALIST can create appointments");

        LocalDateTime startHour = appointment.getAppointmentDate().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endHour = startHour.plusHours(1);

        List<Appointment> conflicts = appointmentRepository.getAppointmentsByEmployeeIdAndAppointmentDateBetween(
                appointment.getEmployeeId(), startHour, endHour
        );
        if (!conflicts.isEmpty()) throw new ApiException( "Appointment time conflicts with another appointment");

        appointment.setCreatedAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }

    public void update(Integer id,Appointment appointment) {
        Employee employee = employeeRepository.getEmployeeById(appointment.getEmployeeId());
        if (employee == null) throw new ApiException( "Employee not found");

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST"))
            throw new ApiException( "Only PC_SPECIALIST can update appointments");

        Appointment old = appointmentRepository.getAppointmentById(id);
        if (old == null) throw new ApiException("Appointment not found");

        LocalDateTime startHour = appointment.getAppointmentDate().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endHour = startHour.plusHours(1);

        List<Appointment> conflicts = appointmentRepository.getAppointmentsByEmployeeIdAndAppointmentDateBetween(
                appointment.getEmployeeId(), startHour, endHour
        );
        conflicts.removeIf(a -> a.getId().equals(id));
        if (!conflicts.isEmpty()) throw new ApiException( "Appointment time conflicts with another appointment");

        old.setCustomerId(appointment.getCustomerId());
        old.setEmployeeId(appointment.getEmployeeId());
        old.setAppointmentDate(appointment.getAppointmentDate());

        appointmentRepository.save(old);
    }

    public void delete(Integer id, Integer employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) throw new ApiException( "Employee not found");

        if (!employee.getRole().equalsIgnoreCase("PC_SPECIALIST")) throw new ApiException("Only PC_SPECIALIST can delete appointments");

        Appointment old = appointmentRepository.getAppointmentById(id);
        if (old == null) throw new ApiException("Appointment not found");

        appointmentRepository.delete(old);
    }


    public Appointment getAppointmentById(Integer id) {
        Appointment appointment =appointmentRepository.getAppointmentById(id);
        if (appointment == null){throw new ApiException("Appointment not found");}
        return appointment;
    }

    public List<Appointment> getAppointmentsByEmployeeId(Integer employeeId) {
        List<Appointment> AppointmentsByEmployeeId = appointmentRepository.getAppointmentsByEmployeeId(employeeId);
        if (AppointmentsByEmployeeId.isEmpty()) {throw new ApiException("No appointments by id "+employeeId+" found");}

        return AppointmentsByEmployeeId;
    }
    public List<Appointment> getAppointmentsByAppointmentDateBefore(LocalDateTime localDateTime){
        List<Appointment> AppointmentsBeforeDate = appointmentRepository.getAppointmentsByAppointmentDateBefore(localDateTime);
        if (AppointmentsBeforeDate.isEmpty()) {throw new ApiException("No appointments by date before  "+localDateTime+" found");}

        return AppointmentsBeforeDate;
    }
}
