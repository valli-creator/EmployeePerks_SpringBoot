package com.MyProject.restdemo.ServiceTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

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
import com.MyProject.restdemo.DAO.ProductDAO;
import com.MyProject.restdemo.Service.EmployeeServiceImpl;
import com.MyProject.restdemo.Service.ProductServiceImpl;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.entity.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

	@Mock
	private ProductDAO productDAO;

	@InjectMocks
	private ProductServiceImpl productService;

	private Product product;
	
	private Product product2;

	@BeforeEach()
	public void init() {
		product = new Product("Iphone", 50,900);
		product.setProductId(1);
		
		product2 = new Product("SunGlasses", 25,50);
		product2.setProductId(2);
	}

	@DisplayName("JUnit test for save Product method")
	@Test
	public void ProductService_CreateProduct_ReturnProduct() throws ResourceNotFoundException {
	
	when(productDAO.save(Mockito.any(Product.class))).thenReturn(product);
	
	Product savedProduct = productService.save(product);
	
	Assertions.assertThat(savedProduct).isNotNull();
	}

	@DisplayName("JUnit test for find all Product method ")
	@Test
	public void ProductService_GetAllProduct_ReturnAllProduct() {
	
//		Page<Employee> employees = Mockito.mock(Page.class);

		when(productDAO.findAll()).thenReturn(List.of(product, product2));

		List<Product> productList = productService.getAllProducts();

		// then - verify the output
		Assertions.assertThat(productList).isNotNull();
		Assertions.assertThat(productList.size()).isEqualTo(2);

	}
	
	@DisplayName("JUnit test for getAllProduct method (negative scenario)")
    @Test
    public void givenEmptyProductList_whenGetAllProduct_thenReturnEmptyProductList(){
        // given - precondition or setup

		Product product1 = new Product("IPod",40,400);
		product1.setProductId(3);

        when(productDAO.findAll()).thenReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Product> productList = productService.getAllProducts();

        // then - verify the output
       Assertions.assertThat(productList).isEmpty();
       Assertions.assertThat(productList.size()).isEqualTo(0);
    }

	@DisplayName("JUnit test for getProductById method")
	@Test
	public void ProductService_GetProductById_ReturnProduct() {
	
		when(productDAO.findById(1)).thenReturn(product);
		
		Product savedProduct = productService.getProductById(product.getProductId());
		
		Assertions.assertThat(savedProduct).isNotNull();
		Assertions.assertThat(savedProduct.getProductName()).isEqualTo(product.getProductName());
		Assertions.assertThat(savedProduct.getQuantity()).isEqualTo(product.getQuantity());
		Assertions.assertThat(savedProduct.getPoints()).isEqualTo(product.getPoints());
		Assertions.assertThat(savedProduct.getProductId()).isEqualTo(product.getProductId());
	}
	

	@DisplayName("JUnit test for updateProduct method")
	 @Test
	 public void ProductService_whenUpdateProduct_thenReturnUpdatedEmployee(){
	
		 when(productDAO.save(product)).thenReturn(product);
		 product.setProductName("Samsung phone");
		 product.setPoints(700);
		 
		 Product updatedProduct = productService.save(product);
		 
		// then - verify the output
		 Assertions.assertThat(updatedProduct.getProductName()).isEqualTo("Samsung phone");
		 Assertions.assertThat(updatedProduct.getPoints()).isEqualTo(700);
}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	public void ProductService_whenDeleteProduct_thenNothing() {

		int productId = 1;
		// when(employeeRepository.deleteById(employeeId)).thenReturn(employeeId);

		productService.deleteById(productId);

		verify(productDAO, times(1)).deleteById(productId);
	}
}
