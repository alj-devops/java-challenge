package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository class to fetch Domain objects from DB
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select id from employee where employee_name=?1 and department=?2", nativeQuery = true)
    Long findId(String name, String department);
}
