package jp.co.axa.apidemo.server.web.v1;

import jp.co.axa.apidemo.client.ClientEmployee;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static jp.co.axa.apidemo.server.web.Paths.*;
import static jp.co.axa.apidemo.server.web.ResourceMapper.*;

@RestController
@RequestMapping(__ + V1 + __ + EMPLOYEES)
public class EmployeeResources {
    
    private static final Logger log = LoggerFactory.getLogger(EmployeeResources.class);
    private EmployeeService employeeService;
    
    @Autowired
    public EmployeeResources(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @DeleteMapping(__ + "{" + ID + "}")
    public void deleteEmployee(@PathVariable(name = ID) final Long id) {
        employeeService.deleteEmployee(id);
        log.info("Employee {} Deleted Successfully", id);
    }
    
    @GetMapping(__ + "{" + ID + "}")
    public ClientEmployee getEmployee(@PathVariable(name = ID) final Long id) {
        log.info("looking for employee which id is {}", id);
        return toClientEmployee(employeeService.getEmployee(id));
    }
    
    @GetMapping()
    public List<ClientEmployee> getEmployees() {
        return toClientEmployeeList(employeeService.retrieveEmployees());
    }
    
    @PostMapping(consumes = "application/json")
    public void saveEmployee(@RequestBody final ClientEmployee input) {
        if(input.getId()!=null){
            log.warn("Id should be generated my repositories. Parameter id ignored here.", input);
            input.setId(null);
        }
        employeeService.saveEmployee(fromClientEmployee(input));
        log.info("Employee {} saved successfully", input);
    }
    
    @PutMapping(consumes = "application/json")
    public void updateEmployee(@RequestBody final Employee input) {
        try{
            employeeService.getEmployee(input.getId());
            employeeService.updateEmployee(input);
            log.info("Employee {} updated successfully", input);
        }
        catch (EmployeeNotFoundException xcpt){
            log.warn("User not found {}. No update", input);
        }
    }
    
}
