package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller class that maps API requests for Employee CRUD operations
 *
 * @author Thea Krista
 * @version 1.0
 * @since api-demo-0.0.1
 */
@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * Get list of all employees
     *
     * @return list of employees with HttpStatus 200
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.retrieveEmployees());
    }

    /**
     * Gets employee information
     *
     * @param employeeId - employee ID to retrieve
     * @return employee information with HttpStatus 200 or HttpStatus 404 if not present
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployee(employeeId);
        if (!employee.isPresent()) {
            log.error("Id ".concat(employeeId.toString()).concat(" is not existing"));
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee.get());
    }

    /**
     * Saves employee information
     *
     * @param employee to save/store
     * @return employee information with HttpStatus 201
     */
    @PostMapping
    public ResponseEntity saveEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        if (savedEmployee == null) {
            log.error("Failed saving employee");
            return ResponseEntity.badRequest().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}").buildAndExpand(savedEmployee.getId()).toUri();
        log.info("Employee saved successfully");
        return ResponseEntity.created(uri).body(savedEmployee);
    }

    /**
     * Deletes employee information
     *
     * @param employeeId - employee ID to retrieve
     * @return HttpStatus 202 if deleted or HttpStatus 400 if not
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Long employeeId) {
        if (!employeeService.getEmployee(employeeId).isPresent()) {
            log.error("Id ".concat(employeeId.toString()).concat(" is not existing"));
            return ResponseEntity.badRequest().build();
        }
        employeeService.deleteEmployee(employeeId);
        String message = "Employee ".concat(employeeId.toString()).concat(" is deleted successfully");
        log.info(message);
        return ResponseEntity.accepted().body(message);
    }

    /**
     * Updates employee information
     *
     * @param employee - information to update
     * @return updated employee data with HTTPStatus of 200
     */
    @PutMapping
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee) {
        if (employee == null || !employeeService.getEmployee(employee.getId()).isPresent()) {
            log.error("Id " + employee.getId() + " is not existing");
            return ResponseEntity.badRequest().build();
        }

        log.info("Employee updated successfully");
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }
}
