package ru.bolnik.fooddelivery.dto.converters;


import ru.bolnik.fooddelivery.dto.OrderDto;
import ru.bolnik.fooddelivery.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverter {

    public OrderDto toDto(Order domain) {
        return new OrderDto(domain.getId(), domain.getAmount());
    }

    public Order toDomain(OrderDto dto) {
        var domain = new Order();
        domain.setId(dto.getId());
        domain.setAmount(dto.getAmount());
        return domain;
    }
}
