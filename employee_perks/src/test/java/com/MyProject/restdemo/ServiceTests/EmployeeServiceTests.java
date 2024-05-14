package com.MyProject.restdemo.ServiceTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.MyProject.restdemo.DAO.EmployeeDAO;
import com.MyProject.restdemo.Service.EmployeeServiceImpl;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.ResourceNotFoundException;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock
	private EmployeeDAO employeeDAO;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employee employee;
	
	private Employee employee2;

	@BeforeEach()
	public void init() {
		employee = new Employee("Valli", "Muthu", "valli@Gmail.com",400);
		employee.setId(1);
		
		employee2 = new Employee("Meenu", "Muthu", "Meenu02@Gmail.com",1000);
		employee2.setId(2);
	}

	@DisplayName("JUnit test for save Employee method")
	@Test
	public void EmployeeService_CreateEmployee_ReturnEmployee() throws ResourceNotFoundException {
	
	when(employeeDAO.save(Mockito.any(Employee.class))).thenReturn(employee);
	
	Employee savedEmployee = employeeService.save(employee);
	
	Assertions.assertThat(savedEmployee).isNotNull();
	}

	@DisplayName("JUnit test for find all Employees method ")
	@Test
	public void EmployeeService_GetAllEmployees_ReturnAllEmployees() {
	
//		Page<Employee> employees = Mockito.mock(Page.class);

		when(employeeDAO.findAll()).thenReturn(List.of(employee, employee2));

		List<Employee> employeeList = employeeService.findAll();

		// then - verify the output
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList.size()).isEqualTo(2);

	}
	
	@DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        // given - precondition or setup

        Employee employee1 = new Employee("Meenu","Muthu","Meenu@gmail.com",500);
        employee1.setId(3);

        when(employeeDAO.findAll()).thenReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.findAll();

        // then - verify the output
       Assertions.assertThat(employeeList).isEmpty();
       Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

	@DisplayName("JUnit test for getEmployeeById method")
	@Test
	public void EmployeeService_GetEmployeeById_ReturnEmployee() {
	
		when(employeeDAO.findById(1)).thenReturn(employee);
		
		Employee savedEmployee = employeeService.findById(employee.getId());
		
		Assertions.assertThat(savedEmployee).isNotNull();
		Assertions.assertThat(savedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
		Assertions.assertThat(savedEmployee.getLastName()).isEqualTo(employee.getLastName());
		Assertions.assertThat(savedEmployee.getEmail()).isEqualTo(employee.getEmail());
		Assertions.assertThat(savedEmployee.getCreditPoints()).isEqualTo(employee.getCreditPoints());
	}
	

	@DisplayName("JUnit test for updateEmployee method")
	 @Test
	 public void EmployeeService_whenUpdateEmployee_thenReturnUpdatedEmployee(){
	
		 when(employeeDAO.save(employee)).thenReturn(employee);
		 employee.setLastName("Ramanathan");
		 employee.setEmail("valliRamanathan@gmail.com");
		 
		 Employee updatedEmployee = employeeService.save(employee);
		 
		// then - verify the output
		 Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("valliRamanathan@gmail.com");
		 Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("Ramanathan");
}

	@DisplayName("JUnit test for deleteEmployee method")
	@Test
	public void EmployeeService_whenDeleteEmployee_thenNothing() {

		int employeeId = 1;
		// when(employeeRepository.deleteById(employeeId)).thenReturn(employeeId);

		employeeService.deleteById(employeeId);

		verify(employeeDAO, times(1)).deleteById(employeeId);
	}
}
