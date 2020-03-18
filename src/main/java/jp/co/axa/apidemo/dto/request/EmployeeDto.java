package jp.co.axa.apidemo.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
@Builder
public class EmployeeDto {
    @ApiParam(value = "The employee's name")
    @NotEmpty(message = "Please enter a name")
    private final String name;
    @ApiParam(value = "The employee's salary")
    @NotNull(message = "Please enter a salary amount")
    @Min(value = 1, message = "You don't pay your employee?! Please enter a number higher than 1.")
    private final Integer salary;
    @ApiParam(value = "The department the employee belongs to")
    @NotEmpty(message = "We need to know what department he/she works at.")
    private final String department;
}
