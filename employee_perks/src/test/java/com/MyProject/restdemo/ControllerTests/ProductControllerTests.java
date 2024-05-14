package com.MyProject.restdemo.ControllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.MyProject.restdemo.Service.EmployeeService;
import com.MyProject.restdemo.Service.ProductService;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.rest.EmployeeRestController;
import com.MyProject.restdemo.rest.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Product product;
	
	private Product product2;
	
	private List<Product> productList = new ArrayList<>();
	
	@BeforeEach()
	public void init() {
		product = new Product("IPhone",25,700);
		product.setProductId(1);
		product2 = new Product("IPods",50,300);
		product2.setProductId(2);
		productList.add(product);
		productList.add(product2);
	}
	
	@DisplayName("JUnit test for createProduct method")
	@Test
	public void ProductController_CreateProduct_ReturnCreated() throws Exception {
	
//		when(employeeService.createEmployee(ArgumentMatchers.any())).thenReturn((invocation -> invocation.getArgument(0)));
		when(productService.save(ArgumentMatchers.any())).thenReturn(product);
		
		ResultActions response = mockMvc.perform(post("/api/products")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(product)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(product.getProductName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	

	@DisplayName("JUnit test for ReturnAllProducts method")
	@Test
	public void ProductController_GetAllProduct_ReturnResponse() throws Exception {
		
		when(productService.getAllProducts()).thenReturn(productList);
		
		ResultActions response = mockMvc.perform(get("/api/products")
								.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(productList.size())))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@DisplayName("JUnit test for GetProductById method")
	@Test
	public void ProductController_GetProductById_ReturnProduct() throws Exception {
	
		int productId = 1;
		when(productService.getProductById(productId)).thenReturn(product);
		
		ResultActions response = mockMvc.perform(get("/api/products/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(product)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(product.getProductName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	@DisplayName("JUnit test for Update method")
	@Test
	public void ProductController_Update_ReturnProduct() throws Exception {
	
		int productId = 1;
		when(productService.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
		
		ResultActions response = mockMvc.perform(put("/api/products")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(product)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(product.getProductName())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	
	@DisplayName("JUnit test for Delete method")
	@Test
	public void ProductController_Delete_ReturnNothing() throws Exception {
	
		int productId = 1;
		
		when(productService.getProductById(productId)).thenReturn(product);

		ResultActions response = mockMvc.perform(delete("/api/products/1")
								.contentType(MediaType.APPLICATION_JSON)
								);
		response.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}
