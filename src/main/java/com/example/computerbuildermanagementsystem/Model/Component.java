package com.example.computerbuildermanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "type must be filled")
    @Pattern(regexp = "^(CPU|GPU|RAM|SSD|PCBUILD)$",message = "type must be CPU or GPU or RAM or SSD or PCBUILD")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @NotEmpty(message = "brand must be filled")
    @Column(columnDefinition = "varchar(20) not null")
    private String brand;

    @NotEmpty(message = "model must be filled")
    @Column(columnDefinition = "varchar(30) not null")
    private String model;

    @NotNull(message = "price must be filled")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "quantity must be filled")
    @Column(columnDefinition = "int not null")
    private Integer quantity;
}
