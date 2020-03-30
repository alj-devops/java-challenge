package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {

        return ResponseEntity.ok(employeeService.retrieveEmployees());
    }


    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee){
        
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee,
                                    @PathVariable(name="employeeId")Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployee(employee, employeeId));

    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        
    }

}
