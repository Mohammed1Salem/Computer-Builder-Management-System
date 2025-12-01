package com.example.computerbuildermanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

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
    @Email
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;

    @NotNull(message = "balance must be filled")
    @Column(columnDefinition = "double not null")
    private Double balance;

    private LocalDateTime registrationDate;
}
