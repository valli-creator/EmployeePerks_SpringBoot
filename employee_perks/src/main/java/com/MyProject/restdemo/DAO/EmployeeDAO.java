package com.MyProject.restdemo.DAO;

import java.util.List;

import com.MyProject.restdemo.entity.Employee;

public interface EmployeeDAO {

	List<Employee> findAll();
	
	Employee findById(int theId);
	
	Employee findByFirstName(String theFirstName);
	
	Employee save(Employee theEmployee);
	
	void deleteById(int theId);
}
