package jp.co.axa.apidemo;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@EnableAutoConfiguration
@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={SpringBootApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //to reset auto-increment id
public class ApiDemoRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void setup() {
        Employee e1 = new Employee(null, "A", 1000, "dep1"); //id is assigned in persist()
        Employee e2 = new Employee(null, "B", 2000, "dep2");
        Employee e3 = new Employee(null, "C", 3000, "dep3");
        testEntityManager.persist(e1);
        testEntityManager.persist(e2);
        testEntityManager.persist(e3);
    }
    @Test
    public void testSaveAndUpdate() {
        // after setup
        assertEquals(employeeRepository.findAll().size(), 3);

        //save a new employee
        Employee e4 = new Employee(10L, "D", 4000, "dep4");//this id is ignored
        employeeRepository.save(e4);
        assertEquals(employeeRepository.findAll().size(), 4);

        // new employee
        Employee emp = employeeRepository.findById(4L).get();
        assertNotNull(emp); // auto-incremented id

        assertFalse(employeeRepository.findById(10L).isPresent());// 10L is ignored
        assertEquals(emp.getSalary(), (Integer)4000);

        //update salary
        Employee e3 = employeeRepository.findById(3L).get();
        e3.setSalary(100000);
        employeeRepository.save(e3);

        Employee newE3 = employeeRepository.findById(3L).get();
        assertNotNull(newE3);
        assertEquals(newE3.getSalary(), (Integer)100000);
    }

    @Test
    public void testDelete() {
        // after setup
        assertEquals(employeeRepository.findAll().size(), 3);

        //delete
        employeeRepository.deleteById(3L);
        assertEquals(employeeRepository.findAll().size(), 2);
        assertFalse(employeeRepository.findById(3L).isPresent());

        //cannot delete twice
        boolean thrown = false;
        try {
            employeeRepository.deleteById(3L);
        } catch (EmptyResultDataAccessException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
