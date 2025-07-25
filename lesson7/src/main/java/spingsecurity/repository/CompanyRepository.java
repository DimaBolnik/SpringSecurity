package spingsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spingsecurity.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
