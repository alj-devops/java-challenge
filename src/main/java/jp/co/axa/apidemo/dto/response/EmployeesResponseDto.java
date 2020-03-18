package jp.co.axa.apidemo.dto.response;

import jp.co.axa.apidemo.entities.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
@Builder
@Getter
/**
 * Holds a list of employees and page information.
 */
public class EmployeesResponseDto {
    private final List<Employee> employees;
    private final int currentPage;
    private final int nextPageNumber;
    private final int pageSize;
    private final int totalPages;
}
