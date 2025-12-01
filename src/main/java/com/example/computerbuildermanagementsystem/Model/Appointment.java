package com.example.computerbuildermanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "customerId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer customerId;

    @NotNull(message = "employeeId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer employeeId;

    @NotNull(message = "appointmentDate must be filled")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime appointmentDate;

    private LocalDateTime createdAt;
}
