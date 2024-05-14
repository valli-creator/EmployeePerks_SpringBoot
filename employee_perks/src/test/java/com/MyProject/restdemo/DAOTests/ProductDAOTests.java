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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.MyProject.restdemo.DAO.ProductDAO;
import com.MyProject.restdemo.DAO.ProductDAOImpl;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class ProductDAOTests {

	@Mock
	private EntityManager theEntityManager;
	
	@Mock
	private TypedQuery<Object> theQuery;
	
	@InjectMocks
	private ProductDAOImpl productDAO;
	
	private Product product;
	
	private Product product2;
	
	@BeforeEach()
	public void init() {
		
		 product = new Product("Iphone",100,500);
		 product.setProductId(1);
		 product2 = new Product("Shirt",50,60);
		 product2.setProductId(2);
	}
	
	@Test
	public void ProductDAO_SaveAll_ReturnsSavedProduct() {
		
		//Arrange 
		when(this.theEntityManager.merge(ArgumentMatchers.any(Product.class)))
		.thenReturn(product);
		
		//Act
		
		Product savedProduct = productDAO.save(product);
		
		//Assert
		
		Assertions.assertThat(savedProduct).isNotNull();
		Assertions.assertThat(savedProduct.getProductId()).isGreaterThan(0);
		
	}
	
	@Test
	public void ProductDAO_GetAll_ReturnMoreThanOneProduct() {
		
		when(this.theEntityManager.createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
		.thenReturn(this.theQuery);
		
		when(this.theQuery.getResultList()).thenReturn(List.of(product,product2));

		
		List<Product> productList = productDAO.findAll();
		
		Assertions.assertThat(productList).isNotNull();
		Assertions.assertThat(productList.size()).isEqualTo(2);
		
	}
	
	@Test
	public void ProductDAO_GetById_ReturnProduct() {
		
		when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
		.thenReturn(product);
		
		Product productDb = productDAO.findById(product.getProductId());
		
		Assertions.assertThat(productDb).isNotNull();
		
}
	
	@Test
	public void ProductDAO_UpdateProduct_ReturnProduct() {
	
		when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
		.thenReturn(product);
		
		when(this.theEntityManager.merge(ArgumentMatchers.any(Product.class)))
		.thenReturn(product);
		
		Product productDb = productDAO.findById(product.getProductId());
		productDb.setProductName("Apple laptop");
		
		Product updatedProduct = productDAO.save(product);
 		Assertions.assertThat(updatedProduct.getProductName()).isNotNull();
		
	}

}
