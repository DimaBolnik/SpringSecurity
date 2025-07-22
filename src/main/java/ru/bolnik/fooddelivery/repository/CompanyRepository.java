package ru.bolnik.fooddelivery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.fooddelivery.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
