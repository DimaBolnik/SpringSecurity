package ru.bolnik.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private long id;

    private double amount;
}
