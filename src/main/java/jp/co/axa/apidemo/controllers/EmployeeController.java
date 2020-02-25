package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.EmployeeDto;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(@Autowired EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @ApiOperation(value = "List all employees on the server.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    @ApiOperation(value = "Get a specific employee on the server.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "employeeId is not found")
    })
    public ResponseEntity<Employee> getEmployee(@PathVariable(name="employeeId") Long employeeId) {
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp != null) {
            return new ResponseEntity(emp, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/employees")
    @ApiOperation(value = "Create a new employee.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public void saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee emp = new Employee();
        emp.setName(employeeDto.getName());
        emp.setSalary(employeeDto.getSalary());
        emp.setDepartment(employeeDto.getDepartment());
        employeeService.saveEmployee(emp);
        System.out.println("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    @ApiOperation(value = "Delete an employee from the server.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "employeeId is not found")
    })
    public HttpStatus deleteEmployee(@PathVariable(name="employeeId") Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            System.out.println("Employee Deleted Successfully");
            return HttpStatus.OK;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return HttpStatus.NOT_FOUND;
        }
    }

    @PutMapping("/employees/{employeeId}")
    @ApiOperation(value = "Update an existing employee with EmployeeDto. Null is allowed for fields of the dto.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "employeeId is not found")
    })
    public HttpStatus updateEmployee(@RequestBody EmployeeDto employeeDto,
                                         @PathVariable(name="employeeId") Long employeeId) {
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp != null) {
            emp.setName(employeeDto.getName());
            emp.setSalary(employeeDto.getSalary());
            emp.setDepartment(employeeDto.getDepartment());
            employeeService.updateEmployee(emp);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
