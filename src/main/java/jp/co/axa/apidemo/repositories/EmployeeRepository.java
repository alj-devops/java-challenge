package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring boot JPA Repository
 * @author AXA
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
