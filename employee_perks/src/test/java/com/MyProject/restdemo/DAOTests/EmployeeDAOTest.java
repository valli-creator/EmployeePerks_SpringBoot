package com.MyProject.restdemo.DAOTests;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.MyProject.restdemo.DAO.EmployeeDAO;
import com.MyProject.restdemo.DAO.EmployeeDAOImpl;
import com.MyProject.restdemo.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class EmployeeDAOTest {

		@Mock
		private EntityManager theEntityManager;
		
		@Mock
		private TypedQuery<Object> theQuery;
		
		@InjectMocks
		private EmployeeDAOImpl employeeDAO;
		
		private Employee employee;
		
		private Employee employee2;
		
		@BeforeEach()
		public void init() {
			
			 employee = new Employee("Valli", "Muthu","valli@Gmail.com",300);
			 employee.setId(1);
			 employee2 = new Employee("Meenu", "Muthu","Meenu@Gmail.com",400);
			 employee2.setId(2);
		}
		
		@Test
		public void EmployeeDAO_SaveAll_ReturnsSavedEmployee() {
			
			//Arrange 
			when(this.theEntityManager.merge(ArgumentMatchers.any(Employee.class)))
				.thenReturn(employee);
			//Act
			
			Employee savedEmployee = employeeDAO.save(employee);
			
			
			//Assert
			
			Assertions.assertThat(savedEmployee).isNotNull();
			Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
			
		}
		
		@Test
		public void EmployeeDAO_GetAll_ReturnMoreThanOneEmployee() {
			
			when(this.theEntityManager.createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
			.thenReturn(this.theQuery);
			
			when(this.theQuery.getResultList()).thenReturn(List.of(employee,employee2));
			
			List<Employee> employeeList = employeeDAO.findAll();
			
			Assertions.assertThat(employeeList).isNotNull();
			Assertions.assertThat(employeeList.size()).isEqualTo(2);
			
		}
		
		@Test
		public void EmployeeDAO_GetById_ReturnEmployee() {
			
			when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
			.thenReturn(employee);
			
			Employee employeeDb = employeeDAO.findById(employee.getId());
			
			Assertions.assertThat(employeeDb).isNotNull();
			
	}
		
		@Test
		public void EmployeeDAO_GetByFirstName_ReturnEmployeeNotNull() {
		
			when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyString()))
			.thenReturn(employee);
			
			Employee employeeDb = employeeDAO.findByFirstName(employee.getFirstName());
			
			Assertions.assertThat(employeeDb).isNotNull();
			
		}
		
		@Test
		public void EmployeeDAO_UpdateEmployee_ReturnEmployee() {
		
			when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
			.thenReturn(employee);
			
			when(this.theEntityManager.merge(ArgumentMatchers.any(Employee.class)))
			.thenReturn(employee);
			
			Employee employeeDb = employeeDAO.findById(employee.getId());
			employeeDb.setLastName("Ramanathan");
			
			Employee updatedEmployee = employeeDAO.save(employee);
	 		Assertions.assertThat(updatedEmployee.getLastName()).isNotNull();
			
		}
		

}
