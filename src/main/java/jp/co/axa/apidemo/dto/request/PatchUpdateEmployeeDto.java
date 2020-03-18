package jp.co.axa.apidemo.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PatchUpdateEmployeeDto {
    @ApiParam(value = "The employee's name")
    private final String name;
    @ApiParam(value = "The employee's salary")
    private final Integer salary;
    @ApiParam(value = "The department the employee belongs to")
    private final String department;
}
