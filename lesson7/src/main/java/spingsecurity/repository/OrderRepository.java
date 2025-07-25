package spingsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spingsecurity.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
