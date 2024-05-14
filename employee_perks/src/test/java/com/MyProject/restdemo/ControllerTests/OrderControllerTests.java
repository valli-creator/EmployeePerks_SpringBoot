package com.MyProject.restdemo.ControllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.sql.Date;
import java.time.Instant;
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

import com.MyProject.restdemo.Service.OrderService;
import com.MyProject.restdemo.Service.ProductService;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.entity.ProductRequest;
import com.MyProject.restdemo.rest.OrderRestController;
import com.MyProject.restdemo.rest.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OrderRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTests {


	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OrderService orderService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Order order;
	
	private Order order2;
	
	private OrderRequest orderRequest;
	
	private OrderRequest orderRequest2;
	
	private List<ProductRequest> productRequestList ;
	
	private List<ProductRequest> productRequestList2;
	
	private List<Order> orderList = new ArrayList<>();
	
	Date date = Date.valueOf("2024-04-25");
	
	@BeforeEach()
	public void init() {
		productRequestList = new ArrayList<>();
		productRequestList2 = new ArrayList<>();
		ProductRequest productRequest = new ProductRequest(1,2,100);
		ProductRequest productRequest2 = new ProductRequest(2,5,50);
		productRequestList.add(productRequest);
		productRequestList.add(productRequest2);
		ProductRequest productRequest3 = new ProductRequest(3,6,10);
		ProductRequest productRequest4 = new ProductRequest(2,5,20);
		productRequestList2.add(productRequest3);
		productRequestList2.add(productRequest4);
		 orderRequest = new OrderRequest(2,productRequestList);   
		 orderRequest2 = new OrderRequest(1,productRequestList2);
		 
		 order = new Order();
		 order2 = new Order();
		 
		 	order.setOrderId(1);
		 	order.setEmployeeId(orderRequest.getEmpId());
			order.setDate(new Date(System.currentTimeMillis()));
		 	order.setDate(date);
			order.setStatus("Detected");
			order.setTotalPoints(100);
			OrderProduct theOrderProduct;
			int theOrderProductId = 1;
//			List<OrderProduct> orderProductList = new ArrayList<>();
			for(ProductRequest products:  productRequestList) {
				
				 theOrderProduct = new OrderProduct();
			theOrderProduct.setOrderProductId(theOrderProductId);
				 
				theOrderProductId++;
	//			theOrderProduct.setProductId(products.getProductId());
				theOrderProduct.setQuantity(products.getQuantity());
				theOrderProduct.setPoints(products.getPoints());
	//			orderProductList.add(theOrderProduct);
			}
	//		order.setOrderProduct(orderProductList);
			order2.setOrderId(2);
			order2.setEmployeeId(orderRequest.getEmpId());
			order2.setDate(new Date(System.currentTimeMillis()));
			order2.setDate(date);
			order2.setStatus("Pending");
			OrderProduct theOrderProduct2;
			int theOrderProductId2 = 1;
//			List<OrderProduct> orderProductList2 = new ArrayList<>();
			for(ProductRequest products:  productRequestList) {
				
				 theOrderProduct2 = new OrderProduct();
				theOrderProduct2.setOrderProductId(theOrderProductId2);
				theOrderProductId2++;
				theOrderProduct2.setProductId(products.getProductId());
				theOrderProduct2.setQuantity(products.getQuantity());
				theOrderProduct2.setPoints(products.getPoints());
//				orderProductList2.add(theOrderProduct2);
			}
//			order2.setOrderProduct(orderProductList2);
			orderList.add(order);
			orderList.add(order2);
	}
	
	@DisplayName("JUnit test for createOrder method")
	@Test
	public void OrderController_CreateOrder_ReturnCreated() throws Exception {
	
//		when(employeeService.createEmployee(ArgumentMatchers.any())).thenReturn((invocation -> invocation.getArgument(0)));
		when(orderService.createOrderForEmployee(ArgumentMatchers.any())).thenReturn(order);
		
		ResultActions response = mockMvc.perform(post("/api/orders")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(order)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(order.getStatus())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.totalPoints", CoreMatchers.is(order.getTotalPoints())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(order.getDate().toString())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.orderId", CoreMatchers.is(order.getOrderId())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.employeeId", CoreMatchers.is(order.getEmployeeId())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	

	@DisplayName("JUnit test for ReturnAllOrders method")
	@Test
	public void OrderController_GetAllOrder_ReturnResponse() throws Exception {
		
		when(orderService.findAll()).thenReturn(orderList);
		
		ResultActions response = mockMvc.perform(get("/api/orders")
								.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(orderList.size())))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@DisplayName("JUnit test for GetOrderById method")
	@Test
	public void OrderController_GetOrderById_ReturnOrder() throws Exception {
	
		int orderId = 1;
		when(orderService.findById(orderId)).thenReturn(order);
		
		ResultActions response = mockMvc.perform(get("/api/orders/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(order)));
	
		//print is optional 
		response.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(order.getStatus())))
	//						.andExpect(MockMvcResultMatchers.jsonPath("$.points", CoreMatchers.is(order.getTotalPoints())))
	//						.andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(order.getDate())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.orderId", CoreMatchers.is(order.getOrderId())))
							.andExpect(MockMvcResultMatchers.jsonPath("$.employeeId", CoreMatchers.is(order.getEmployeeId())))
							.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	@DisplayName("JUnit test for Delete method")
	@Test
	public void OrderController_Delete_ReturnNothing() throws Exception {
	
		int orderId = 1;
		
		when(orderService.findById(orderId)).thenReturn(order);

		ResultActions response = mockMvc.perform(delete("/api/orders/1")
								.contentType(MediaType.APPLICATION_JSON)
								);
		response.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
}
