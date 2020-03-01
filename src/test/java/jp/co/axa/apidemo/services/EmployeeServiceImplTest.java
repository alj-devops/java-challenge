package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {
    
    private final DefaultEmployeeService service;
    private final EmployeeRepository repository;
    private static final long NON_EXISTING_ID = 5000;
    
    public EmployeeServiceImplTest() {
        repository = Mockito.mock(EmployeeRepository.class);
        service = new DefaultEmployeeService(repository);
    }
    
    @Test(expected = EmployeeNotFoundException.class)
    public void EmployeeNotFoundTest() {
        when(repository.findById(NON_EXISTING_ID)).thenReturn(empty());
        service.getEmployee(NON_EXISTING_ID);
    }
}
