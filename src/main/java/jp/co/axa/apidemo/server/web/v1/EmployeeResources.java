package jp.co.axa.apidemo.server.web.v1;

import jp.co.axa.apidemo.client.ClientEmployee;
import jp.co.axa.apidemo.entities.Employee;
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
        return toClientEmployee(employeeService.getEmployee(id));
    }
    
    @GetMapping()
    public List<ClientEmployee> getEmployees() {
        return toClientEmployeeList(employeeService.retrieveEmployees());
    }
    
    @PostMapping()
    public void saveEmployee(final ClientEmployee input) {
        employeeService.saveEmployee(fromClientEmployee(input));
        log.info("Employee {} Saved Successfully", input);
    }
    
    @PutMapping(__ + "{" + ID + "}")
    public void updateEmployee(@RequestBody final Employee employee,
                               @PathVariable(name = ID) final Long id) {
        Employee emp = employeeService.getEmployee(id);
        if (emp != null) {
            employeeService.updateEmployee(employee);
        }
    }
    
}
