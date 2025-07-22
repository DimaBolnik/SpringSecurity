package ru.bolnik.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.fooddelivery.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
