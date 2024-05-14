package com.MyProject.restdemo.service;

import java.util.List;

import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;

public interface ProductService {

List<Product> getAllProducts();

Product getProductById(int theId);

Product save(Product theProduct);
	
void deleteById(int theId);
	
}
