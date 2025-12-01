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
public class PCBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "CPU id must be filled")
    @Column(columnDefinition = "int not null")
    private Integer cpuId;

    @NotNull(message = "GPU id must be filled")
    @Column(columnDefinition = "int not null")
    private Integer gpuId;

    @NotNull(message = "RAM id must be filled")
    @Column(columnDefinition = "int not null")
    private Integer ramId;

    @NotNull(message = "SSD id must be filled")
    @Column(columnDefinition = "int not null")
    private Integer ssdId;

    @Column(columnDefinition = "double")
    private Double price;

    private LocalDateTime createdAt;
}
