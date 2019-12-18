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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import jp.co.axa.apidemo.entities.Employee;

/**
 * Test for the router handler
 * @author li.han
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmployeeControllerTest {

	// Web Context
	@Autowired
    private WebApplicationContext context;
	// mvc 
    private MockMvc mvc;
    //RequestBody
    private String requestJson;
    
    // Initialize the test object 
    @Before
    public void setup() throws JsonProcessingException{
    	mvc = MockMvcBuilders.webAppContextSetup(context).build();
    	//Create the request Json
		Employee employee = new Employee();
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    requestJson=ow.writeValueAsString(employee);
    }

    //Test for the GetEmployees information List 
	@Test
	public void testGetEmployees() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	//Test for the GetEmployee information
	@Test
	public void testGetEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/{employeeId}","123"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	//Test for the Save Employee information
	@Test
	public void testSaveEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());	}
 
	//Test for the Delete Employee information
	@Test
	public void testDeleteEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.delete("/api/v1/employees/{employeeId}","123"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());	
	}

	//Test for the update the Employee information
	@Test
	public void testUpdateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.put("/api/v1/employees/{emplyeeId}","123").contentType(MediaType.APPLICATION_JSON).content(requestJson))
		.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());		
	}

}
