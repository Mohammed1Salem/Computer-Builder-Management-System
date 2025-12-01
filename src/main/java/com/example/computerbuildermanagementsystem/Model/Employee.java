package com.example.computerbuildermanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must be filled")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "password must be filled")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = "email must be filled")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;

    @NotEmpty(message = "role must be ASSEMBLER, DELIVERY_DRIVER, or PC_SPECIALIST")
    @Pattern(regexp = "ASSEMBLER|DELIVERY_DRIVER|PC_SPECIALIST",
            message = "role must be ASSEMBLER, DELIVERY_DRIVER, or PC_SPECIALIST")
    @Column(columnDefinition = "varchar(20) not null")
    private String role;

    private LocalDateTime hiredDate;
}
