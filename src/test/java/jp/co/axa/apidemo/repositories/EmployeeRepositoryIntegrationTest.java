package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByID_returnEmployee(){
        Employee newEmployee = new Employee( "TK", 12, "IT Dept");
        entityManager.persist(newEmployee);
        entityManager.flush();

        Optional<Employee> employee = employeeRepository.findById(newEmployee.getId());

        assertThat(employee.isPresent()).isTrue();
        assertThat(employee.get().getName()).isEqualTo(newEmployee.getName());
    }

    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Employee tk = new Employee("TK", 12, "IT Dept");
        Employee thea = new Employee("thea", 12, "Sales Dept");
        Employee krista = new Employee("krista", 12, "HR Dept");

        entityManager.persist(tk);
        entityManager.persist(thea);
        entityManager.persist(krista);
        entityManager.flush();

        List<Employee> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(tk.getName(),
                thea.getName(), krista.getName());
    }

    @Test
    public void whenSaveEmployee_thenCreateEmployee(){
        Employee newEmployee = new Employee("TK", 12, "IT Dept");
        Employee savedEmployee = employeeRepository.save(newEmployee);
        assertThat(savedEmployee.getName()).isEqualTo(newEmployee.getName());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenDeleteEmployee(){
        Employee newEmployee = new Employee("Casupanan", 12, "IT Dept");
        entityManager.persist(newEmployee);
        entityManager.flush();

        Optional<Employee> employee = employeeRepository.findById(newEmployee.getId());

        assertThat(employee.isPresent()).isTrue();
        assertThat(employee.get().getName()).isEqualTo(newEmployee.getName());

        employeeRepository.deleteById(newEmployee.getId());

        Optional<Employee> afterDelete = employeeRepository.findById(newEmployee.getId());

        assertThat(afterDelete.isPresent()).isFalse();
    }

}
