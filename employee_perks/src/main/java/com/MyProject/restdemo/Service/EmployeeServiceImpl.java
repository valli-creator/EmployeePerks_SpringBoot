package com.MyProject.restdemo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MyProject.restdemo.DAO.EmployeeDAO;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

private EmployeeDAO employeeDAO;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO theEmployeeDAO) {
		employeeDAO = theEmployeeDAO;
	}

	@Override
	public List<Employee> findAll() {
		
		return employeeDAO.findAll();
	}
	
	 @Override
    public Employee findById(int theId) {
        Optional<Employee> result = Optional.ofNullable(employeeDAO.findById(theId));

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return theEmployee;
    } 
	 
	 @Override
	    public Employee findByFirstName(String theFirstname) {
	        Optional<Employee> result = Optional.ofNullable(employeeDAO.findByFirstName(theFirstname));

	        Employee theEmployee = null;

	        if (result.isPresent()) {
	            theEmployee = result.get();
	        }
	        else {
	            throw new RuntimeException("Did not find employee first name - " + theFirstname);
	        }
	        return theEmployee;
	    }
	
	@Transactional
	@Override
	public Employee save(Employee theEmployee) {
		
		return employeeDAO.save(theEmployee);
	}

	@Transactional
	@Override
	public void deleteById(int theId) {
		employeeDAO.deleteById(theId);
		
	}
}
