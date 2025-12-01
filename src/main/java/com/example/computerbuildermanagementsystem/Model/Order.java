package com.example.computerbuildermanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "customerId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer customerId;

    @NotNull(message = "itemId must be filled")
    @Column(columnDefinition = "int not null")
    private Integer itemId;

    @NotNull(message = "itemType must be filled")
    @Column(columnDefinition = "varchar(20) not null")
    private String itemType;

    private LocalDateTime orderDate;

    @NotNull
    @Pattern(regexp = "ASSEMBLING|OUT_FOR_DELIVERY|DELIVERED", message = "status must be ASSEMBLING, OUT_FOR_DELIVERY, or DELIVERED")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;
}
