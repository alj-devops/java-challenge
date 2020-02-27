package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to fetch/persist employee entity
 *
 * @author Thea Krista
 * @version 1.0
 * @since api-demo-0.0.1
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
