package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.axa.apidemo.dto.response.EmployeesResponseDto;
import jp.co.axa.apidemo.dto.response.ErrorResponseDto;
import jp.co.axa.apidemo.dto.request.EmployeeDto;
import jp.co.axa.apidemo.dto.request.PatchUpdateEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public EmployeesResponseDto getEmployees(@ApiParam(value = "the current page in the pagination.", defaultValue = "0")
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @ApiParam(value = "the size of each page.", defaultValue = "25")
                                             @RequestParam(value = "size", required = false, defaultValue = "25") int size) {
        return employeeService.retrieveEmployees(page, size);
    }

    @GetMapping("/employees/{employeeId}")
    @ApiResponses({@ApiResponse(code = 404, message = "Not Found", response = ErrorResponseDto.class)})
    public Employee getEmployee(@ApiParam(value = "The employee's id")
                                    @PathVariable(name="employeeId") @Min(1) Long employeeId) throws EmployeeNotFoundException {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@ApiParam(value = "A json that has the salary, department, and name of the employee") @Validated @RequestBody EmployeeDto createEmployeeDto){
        Employee employee = employeeService.saveEmployee(createEmployeeDto);
        log.debug("Employee Saved Successfully: {}", employee);
        return employee;
    }

    @DeleteMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({@ApiResponse(code = 404, message = "Not Found", response = ErrorResponseDto.class)})
    public void deleteEmployee(@ApiParam(value = "The employee's id") @PathVariable(name="employeeId") @Min(1) Long employeeId) throws EmployeeNotFoundException{
        employeeService.deleteEmployee(employeeId);
        log.debug("Employee with id {} was deleted successfully", employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({@ApiResponse(code = 404, message = "Not Found", response = ErrorResponseDto.class)})
    public void updateEmployee(@ApiParam(value = "A json that has the salary, department, and name of the employee") @RequestBody EmployeeDto employee,
                               @ApiParam(value = "The employee's id")  @PathVariable(name="employeeId") @Min(1)  Long employeeId) throws EmployeeNotFoundException{
        employeeService.updateEmployee(employeeId, employee);
        log.debug("Employee with id {} was updated successfully", employeeId);
    }

    @PatchMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({@ApiResponse(code = 404, message = "Not Found", response = ErrorResponseDto.class)})
    public void patchUpdateEmployee(@RequestBody PatchUpdateEmployeeDto employee,
                                    @ApiParam(value = "The employee's id") @PathVariable(name="employeeId") @Min(1)  Long employeeId) throws EmployeeNotFoundException{
        employeeService.patchUpdateEmployee(employeeId, employee);
        log.debug("Employee with id {} was updated successfully", employeeId);
    }
}
