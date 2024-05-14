package com.MyProject.restdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.MyProject.restdemo.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

	//define field for entity manager
	
		
	private EntityManager entityManager;
		
		//set up constructor injection
		public EmployeeDAOImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		@Override
		public List<Employee> findAll() {
			
			//create a query
			TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);
			
			
			//execute query and get result set
			List<Employee> employees = new ArrayList();
			employees = theQuery.getResultList();
			
			//return the results
			
			return employees;
		}

		@Override
		public Employee findById(int theId) {
			//get employee
			
			Employee theEmployee = entityManager.find(Employee.class, theId);
			//return employee
			
			return theEmployee;
		}
		
		@Override
		public Employee findByFirstName(String theFirstName) {
			//get employee
			
			Employee theEmployee = entityManager.find(Employee.class,theFirstName );
			//return employee
			
			return theEmployee;
		}

		@Override
		public Employee save(Employee theEmployee) {
			//save employee
			//save or update depending on id of the entity
			
			Employee dbEmployee = entityManager.merge(theEmployee);
			
			//return the dbEmployee
			
			return dbEmployee;
		}

		@Override
		public void deleteById(int theId) {
			//find employee by id
			
			Employee theEmployee = entityManager.find(Employee.class,theId);
			
			//remove employee
			entityManager.remove(theEmployee);
		}

}
