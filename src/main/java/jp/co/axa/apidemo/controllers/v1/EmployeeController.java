package jp.co.axa.apidemo.controllers.v1;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jp.co.axa.apidemo.controllers.Paths.*;

@RestController
@RequestMapping(__ + V1 + __ + EMPLOYEES)
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @DeleteMapping(__ + "{" + ID + "}")
    public void deleteEmployee(@PathVariable(name = ID) final Long id) {
        employeeService.deleteEmployee(id);
        System.out.println("Employee Deleted Successfully");
    }
    
    @GetMapping(__ + "{" + ID + "}")
    public Employee getEmployee(@PathVariable(name = ID) final  Long id) {
        return employeeService.getEmployee(id);
    }
    
    @GetMapping()
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }
    
    @PostMapping()
    public void saveEmployee(final Employee employee) {
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }
    
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @PutMapping(__ + "{" + ID + "}")
    public void updateEmployee(@RequestBody final  Employee employee,
                               @PathVariable(name = ID) final Long id) {
        Employee emp = employeeService.getEmployee(id);
        if (emp != null) {
            employeeService.updateEmployee(employee);
        }
    }
    
}
