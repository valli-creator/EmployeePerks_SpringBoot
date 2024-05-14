package com.MyProject.restdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MyProject.restdemo.dao.EmployeeDAO;
import com.MyProject.restdemo.dao.ProductDAO;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.Product;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	@Autowired
	public ProductServiceImpl(ProductDAO theProductDAO) {
		productDAO = theProductDAO;
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productDAO.findAll();
	}

	@Override
    public Product getProductById(int theId) {
        Optional<Product> result = Optional.ofNullable(productDAO.findById(theId));

        Product theProduct = null;

        if (result.isPresent()) {
            theProduct = result.get();
        }
        else {
        	throw new RuntimeException("Did not find Product id - " + theId);
        }
        return theProduct;
    }

	@Override
	@Transactional
	public Product save(Product theProduct) {
		
		return productDAO.save(theProduct);
	}

	@Override
	public void deleteById(int theId) {
		productDAO.deleteById(theId);
		
	}
}
