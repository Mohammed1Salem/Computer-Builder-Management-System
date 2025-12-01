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
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "orderId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer orderId;

    @NotNull(message = "deliveryEmployeeId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer deliveryEmployeeId;

    @NotNull(message = "deliveryDate must be filled")
    @Column(columnDefinition = "timestamp not null")
    private LocalDateTime deliveryDate;

    private LocalDateTime createdAt;
}
