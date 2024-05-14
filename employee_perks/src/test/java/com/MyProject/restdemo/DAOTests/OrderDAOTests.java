package com.MyProject.restdemo.DAOTests;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.MyProject.restdemo.DAO.OrderDAO;
import com.MyProject.restdemo.DAO.OrderDAOImpl;
import com.MyProject.restdemo.DAO.ProductDAOImpl;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.entity.ProductRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class OrderDAOTests {

	@Mock
	private EntityManager theEntityManager;
	
	@Mock
	private TypedQuery<Object> theQuery;
	
	@InjectMocks
	private OrderDAOImpl orderDAO;
	
	private OrderRequest orderRequest;
	
	private OrderRequest orderRequest2;
	
	private Order order;
	
	private Order order2;
	
	private List<ProductRequest> productRequestList;
	
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
	
	@Test
	public void OrderDAO_SaveAll_ReturnsSavedOrders() {
			
		lenient().when(this.theEntityManager.merge(ArgumentMatchers.any(Order.class)))
		.thenReturn(order);
		
		Order savedOrder = orderDAO.save(order);
		Order savedOrder2 = orderDAO.save(order2);
		
		//Assert
		
		Assertions.assertThat(savedOrder).isNotNull();
		Assertions.assertThat(savedOrder.getOrderId()).isGreaterThan(0);
		Assertions.assertThat(savedOrder2).isNotNull();
		Assertions.assertThat(savedOrder2.getOrderId()).isGreaterThan(0);
		
	}
	
	@Test
	public void OrderDAO_GetAll_ReturnMoreThanOneOrder() {
		
		
		when(this.theEntityManager.createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
		.thenReturn(this.theQuery);
		
		when(this.theQuery.getResultList()).thenReturn(List.of(order,order2));
		
		List<Order> orderList = orderDAO.findAll();
		List<Order> orderList2 = orderDAO.findAll();
		
		Assertions.assertThat(orderList).isNotNull();
		Assertions.assertThat(orderList.size()).isEqualTo(2);
		Assertions.assertThat(orderList2).isNotNull();
		Assertions.assertThat(orderList2.size()).isEqualTo(2);
		
	}
	
	@Test
	public void OrderDAO_GetById_ReturnOrder() {
		
		when(this.theEntityManager.find(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
		.thenReturn(order);
		
		Order orderDb = orderDAO.findById(order.getOrderId());
		
		Assertions.assertThat(orderDb).isNotNull();
		
}


}
