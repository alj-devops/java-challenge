package jp.co.axa.apidemo.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Test for the router handler.
 *
 * @author li.han
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmployeeControllerTest {

    /**
     * Web Application Context.
     */
    @Autowired
    private WebApplicationContext context;
    /**
     * test mvc.
     */
    private MockMvc mvc;

    /**
     * RequestBody when id is null.
     */
    private String requestJsonWithNullId = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when id is not exist.
     */
    private String requestJsonWithoutId = "{\"name\":\"TestName\"," + "\"salary\":1,"
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when id is Long.
     */
    private String requestJsonWithNumId = "{\"id\":123321," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"TestDepartment\"}";

    /**
     * RequestBody when id is Num String.
     */
    private String requestJsonWithStringNumId = "{\"id\":\"123321\"," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when id is Blank.
     */
    private String requestJsonWithBlankId = "{\"id\":\"\"," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when id is String.
     */
    private String requestJsonWithStringId = "{\"id\":\"abc\"," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody without Name.
     */
    private String requestJsonWithoutName = "{\"id\":null," + "\"salary\":1,"
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody with Null Name.
     */
    private String requestJsonWithNullName = "{\"id\":null," + "\"name\":null," + "\"salary\":1,"
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody with Number Name.
     */
    private String requestJsonWithNumName = "{\"id\":null," + "\"name\":123," + "\"salary\":1,"
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody with Blank Name.
     */
    private String requestJsonWithBlankName = "{\"id\":null," + "\"name\":\"\"," + "\"salary\":1,"
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody without Department.
     */
    private String requestJsonWithoutDepartment = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":1" + "}";

    /**
     * RequestBody with Null Department.
     */
    private String requestJsonWithNullDepartment = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":null" + "}";

    /**
     * RequestBody with number Department.
     */
    private String requestJsonWithNumDepartment = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":123" + "}";

    /**
     * RequestBody with Blank Department.
     */
    private String requestJsonWithBlankDepartment = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":1," + "\"department\":\"\"" + "}";

    /**
     * RequestBody without Salary.
     */
    private String requestJsonWithoutSalary = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody with null Salary.
     */
    private String requestJsonWithNullSalary = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":null," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody with String Salary, but String is number.
     */
    private String requestJsonWithNumStringSalary = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":\"123\"," + "\"department\":\"TestDepartment\"" + "}";;

    /**
     * RequestBody with String Salary.
     */
    private String requestJsonWithStringSalary = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":\"abc\"," + "\"department\":\"TestDepartment\"" + "}";;

    /**
     * RequestBody when salary is less than 0.
     */
    private String requestJsonWithSalaryMinus = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":-1," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when salary is 0.
     */
    private String requestJsonWithSalaryZero = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":0," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * RequestBody when salary is Blank.
     */
    private String requestJsonWithBlankSalary = "{\"id\":null," + "\"name\":\"TestName\","
            + "\"salary\":\"\"," + "\"department\":\"TestDepartment\"" + "}";

    /**
     * Initial the Test Context.
     *
     * @throws JsonProcessingException for test initial
     */
    @Before
    public void setup() throws JsonProcessingException {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Test for Get employees List.
     *
     * @throws Exception for get employee test
     */
    @Test
    public void testGetEmployees() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for Get employee.
     *
     * @throws Exception
     */
    @Test
    public void testGetEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/{employeeId}", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for Get employee Exception.
     *
     * @throws Exception
     */
    @Test
    public void testGetEmployeeException() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/{employeeId}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for Save the employee.
     *
     * @throws Exception for save test
     */
    @Test
    public void testSaveEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringNumId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumDepartment))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithSalaryZero))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumStringSalary))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for the invalid JSON test cases. And Bad parameter request.
     *
     * @throws Exception
     */
    @Test
    public void testSaveEmployeeException() throws Exception {
        // Bad JSON data
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithSalaryMinus))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        // bad parameter request
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/{employeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for delete the employee.
     *
     * @throws Exception for delete test
     */
    @Test
    public void testDeleteEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/{employeeId}", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for delete the employ exception.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteEmployeeException() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/{employeeId}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for update the employee.
     *
     * @throws Exception for update test
     */
    @Test
    public void testUpdateEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringNumId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumDepartment))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithSalaryZero))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNumStringSalary))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for the invalid test input cases. Test for the Bad parameter request.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateEmployeeException() throws Exception {
        // Bad JSON
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankDepartment))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithSalaryMinus))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithoutSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithStringSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithBlankSalary))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        // Bad parameter test
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "123"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/{emplyeeId}", "abc")
                .contentType(MediaType.APPLICATION_JSON).content(requestJsonWithNullId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
