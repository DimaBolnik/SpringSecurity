package spingsecurity.dto.converters;

import org.springframework.stereotype.Component;
import spingsecurity.dto.OrderDto;
import spingsecurity.model.Order;

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
