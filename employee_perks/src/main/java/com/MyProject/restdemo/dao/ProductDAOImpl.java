package com.MyProject.restdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.MyProject.restdemo.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ProductDAOImpl implements ProductDAO {
	
	private EntityManager theEntityManager;
	
	public ProductDAOImpl(EntityManager theEntityManager) {
		super();
		this.theEntityManager = theEntityManager;
	}

	@Override
	public List<Product> findAll() {
		
		//create query
		
		TypedQuery<Product> theQuery = theEntityManager.createQuery("from Product" , Product.class);
		
	List<Product> products = new ArrayList<Product>();
			products = theQuery.getResultList();
		return products;
	}
		

	@Override
	public Product findById(int theId) {
		//get product
		
		Product theProduct = theEntityManager.find(Product.class, theId);
		//return product
		
		return theProduct;
		
	}

	@Override
	public Product save(Product theProduct) {
		//save product
		//save or update depending on id of the entity
		
		Product dbProduct = theEntityManager.merge(theProduct);
		
		//return the dbProduct
		
		return dbProduct;
	}

	@Override
	public void deleteById(int theId) {
		//get product
		Product theProduct = theEntityManager.find(Product.class, theId);
		
		//delete product
		theEntityManager.remove(theProduct);
		
	}
	
	
}
