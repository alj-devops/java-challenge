package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.TestData;
import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeController.class, secure = false)
public class EmployeeControllerTest extends TestData {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void success_getEmployeesList() throws Exception {
        when(employeeService.retrieveEmployees(anyInt(), anyInt())).thenReturn(getEmployeesDto());
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].id").value(1))
                .andExpect(jsonPath("$.totalPages").value(5))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.nextPageNumber").value(1));
    }

    @Test
    public void success_getEmployee() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(getSingleEmployee());
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.department").value("department 0"))
                .andExpect(jsonPath("$.name").value("name 0"))
                .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    public void failed_getEmployee_employeeNotFound() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenThrow(new EmployeeNotFoundException());
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("EMPLOYEE_NOT_FOUND"))
                .andExpect(jsonPath("$.errorCode").value("9111001"));
    }

    @Test
    public void success_createEmployee() throws Exception {
        when(employeeService.saveEmployee(any())).thenReturn(getBobEmployee());
        mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(createRequestString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.department").value("HR"))
                .andExpect(jsonPath("$.name").value("bob"))
                .andExpect(jsonPath("$.salary").value(111));
    }

    @Test
    public void success_deleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(createRequestString))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void failed_deleteEmployee_notFound() throws Exception {
        Mockito.doThrow(new EmployeeNotFoundException()).when(employeeService).deleteEmployee(anyLong());
        mockMvc.perform(delete("/api/v1/employees/2").contentType(MediaType.APPLICATION_JSON_UTF8).content(createRequestString))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("EMPLOYEE_NOT_FOUND"))
                .andExpect(jsonPath("$.errorCode").value("9111001"));
    }

    @Test
    public void success_updateEmployee() throws Exception {
        mockMvc.perform(put("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(updateRequestString))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void success_patchUpdateEmployee() throws Exception {
        mockMvc.perform(put("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(patchUpdateRequestString))
                .andExpect(status().is2xxSuccessful());
    }
}
