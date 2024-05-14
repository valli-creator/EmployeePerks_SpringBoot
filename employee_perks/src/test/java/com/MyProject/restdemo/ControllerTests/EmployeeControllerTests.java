package com.MyProject.restdemo.ControllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.MyProject.restdemo.Service.EmployeeService;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.rest.EmployeeRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Employee employee;
	
	private Employee employee2;
	
	private List<Employee> employees = new ArrayList<>();
	
	// Declare logger
		private static final Logger logger = Logger.getLogger(EmployeeControllerTests.class.getName());
		
		@BeforeAll
	    static void setup() {
	        logger.log(Level.INFO,"startup - Testing starts");
	    }

	    @AfterAll
	    static void tearDown() {
	    	logger.log(Level.INFO,"Testing ends");
	    }
	   

	 @BeforeEach()
	public void init() {
		logger.log(Level.INFO,"startup");
		employee = new Employee("Valli", "Muthu", "valli@Gmail.com",900);
		employee.setId(1);
		employee2 = new Employee("Eva", "John", "Eva@Gmail.com",3000);
		employee2.setId(2);
		employees.add(employee);
		employees.add(employee2);
	}
	
	@AfterEach
    void teardown() {
        logger.log(Level.INFO,"teardown");
    }
	
	@DisplayName("JUnit test for createEmployee method")
	@Test
	public void EmployeeController_CreateEmployee_ReturnCreated() throws Exception {
	

		when(employeeService.save(ArgumentMatchers.any())).thenReturn(employee);
		
		ResultActions response = mockMvc.perform(post("/api/employees")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(employee)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	

	@DisplayName("JUnit test for ReturnAllEmployees method")
	@Test
	public void EmployeeController_GetAllEmployees_ReturnResponse() throws Exception {
		
		when(employeeService.findAll()).thenReturn(employees);
		
		ResultActions response = mockMvc.perform(get("/api/employees")
								.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employees.size())))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@DisplayName("JUnit test for GetEmployeeById method")
	@Test
	public void EmployeeController_GetEmployeeById_ReturnEmployee() throws Exception {
	
		int empId = 1;
		when(employeeService.findById(empId)).thenReturn(employee);
		
		ResultActions response = mockMvc.perform(get("/api/employees/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(employee)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	@DisplayName("JUnit test for Update method")
	@Test
	public void EmployeeController_Update_ReturnEmployee() throws Exception {
	
		int empId = 1;
		when(employeeService.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
		
		ResultActions response = mockMvc.perform(put("/api/employees")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(employee)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	
	@DisplayName("JUnit test for Delete method")
	@Test
	public void EmployeeController_Delete_ReturnNothing() throws Exception {
	
		int empId = 1;
		
		when(employeeService.findById(empId)).thenReturn(employee);

		ResultActions response = mockMvc.perform(delete("/api/employees/1")
								.contentType(MediaType.APPLICATION_JSON)
								);
		response.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}
