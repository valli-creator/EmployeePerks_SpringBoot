package com.MyProject.restdemo.DAO;

import java.util.List;

import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;

public interface ProductDAO {

	List<Product> findAll();
	
	Product findById(int theId);
	
	Product save(Product theProduct);
	
	void deleteById(int theId);
	
}
