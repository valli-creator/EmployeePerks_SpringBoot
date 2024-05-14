package com.MyProject.restdemo.ServiceTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.MyProject.restdemo.DAO.OrderDAO;
import com.MyProject.restdemo.DAO.OrderProductDAO;
import com.MyProject.restdemo.DAO.ProductDAO;
import com.MyProject.restdemo.Service.EmployeeService;
import com.MyProject.restdemo.Service.OrderServiceImpl;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.entity.ProductRequest;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	private EmployeeService employeeService;
	
	@Mock 
	private ProductDAO productDao;
	
	@InjectMocks
	private OrderServiceImpl orderService;

	private Order order;
	
	private Order order2;
	
private OrderRequest orderRequest;
	
	private OrderRequest orderRequest2;
	
private List<ProductRequest> productRequestList ;
	
	private List<ProductRequest> productRequestList2;

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
			order.setStatus("Detected");
			for(ProductRequest products:  productRequestList) {
				
				OrderProduct theOrderProduct = new OrderProduct();
				theOrderProduct.setOrderProductId(1);
				theOrderProduct.setProductId(products.getProductId());
				theOrderProduct.setQuantity(products.getQuantity());
				theOrderProduct.setPoints(products.getPoints());
	}
			order2.setOrderId(2);
			order2.setEmployeeId(orderRequest.getEmpId());
			order2.setDate(new Date(System.currentTimeMillis()));
			order2.setStatus("Pending");
			for(ProductRequest products:  productRequestList) {
				
				OrderProduct theOrderProduct2 = new OrderProduct();
				theOrderProduct2.setOrderProductId(2);
				theOrderProduct2.setProductId(products.getProductId());
				theOrderProduct2.setQuantity(products.getQuantity());
				theOrderProduct2.setPoints(products.getPoints());
			}
	}

	@DisplayName("JUnit test for save Order method")
	@Test
	public void OrderService_CreateOrder_ReturnOrder() throws Exception {
	
	when(employeeService.findById(ArgumentMatchers.anyInt())).thenReturn(new Employee("fname", "lname", "email@gmail.com", 2000));
	when(productDao.findById(ArgumentMatchers.anyInt())).thenReturn(new Product("Lotion", 10, 100));
	
	Order savedOrder = orderService.createOrderForEmployee(orderRequest);
	
	Assertions.assertThat(savedOrder).isNotNull();
	}

	@DisplayName("JUnit test for find all Order method ")
	@Test
	public void OrderService_GetAllOrder_ReturnAllOrders() {

		when(orderDAO.findAll()).thenReturn(List.of(order, order2));

		List<Order> orderList = orderService.findAll();

		// then - verify the output
		Assertions.assertThat(orderList).isNotNull();
		Assertions.assertThat(orderList.size()).isEqualTo(2);

	}
	
	@DisplayName("JUnit test for getAllOrder method (negative scenario)")
    @Test
    public void givenEmptyOrderList_whenGetAllOrder_thenReturnEmptyOrderList(){
        // given - precondition or setup

		ProductRequest productRequest = new ProductRequest(1,2,100);
		productRequestList.add(productRequest);
		 orderRequest = new OrderRequest(2,productRequestList); 
		 order.setOrderId(1);
		 	order.setEmployeeId(orderRequest.getEmpId());
			order.setDate(new Date(System.currentTimeMillis()));
			order.setStatus("Detected");
			for(ProductRequest products:  productRequestList) {
				
				OrderProduct theOrderProduct = new OrderProduct();
				theOrderProduct.setOrderProductId(1);
				theOrderProduct.setProductId(products.getProductId());
				theOrderProduct.setQuantity(products.getQuantity());
				theOrderProduct.setPoints(products.getPoints());
	}
		 

        when(orderDAO.findAll()).thenReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Order> orderList = orderService.findAll();

        // then - verify the output
       Assertions.assertThat(orderList).isEmpty();
       Assertions.assertThat(orderList.size()).isEqualTo(0);
    }

	@DisplayName("JUnit test for getOrderById method")
	@Test
	public void OrderService_GetOrderById_ReturnOrder() {
	
		when(orderDAO.findById(1)).thenReturn(order);
		
		Order savedOrder = orderService.findById(order.getOrderId());
		
		Assertions.assertThat(savedOrder).isNotNull();
		Assertions.assertThat(savedOrder.getOrderId()).isEqualTo(order.getOrderId());
		Assertions.assertThat(savedOrder.getTotalPoints()).isEqualTo(order.getTotalPoints());
		Assertions.assertThat(savedOrder.getDate()).isEqualTo(order.getDate());
		Assertions.assertThat(savedOrder.getEmployeeId()).isEqualTo(order.getEmployeeId());
		Assertions.assertThat(savedOrder.getStatus()).isEqualTo(order.getStatus());
		Assertions.assertThat(savedOrder.getOrderProduct()).isEqualTo(order.getOrderProduct());
	}
	

	@DisplayName("JUnit test for deleteOrder method")
	@Test
	public void OrderService_whenDeleteOrder_thenNothing() {

		int orderId = 1;
		// when(employeeRepository.deleteById(employeeId)).thenReturn(employeeId);

		orderService.cancelOrderById(orderId);

		verify(orderDAO, times(1)).cancelOrderById(orderId);
	}
}
