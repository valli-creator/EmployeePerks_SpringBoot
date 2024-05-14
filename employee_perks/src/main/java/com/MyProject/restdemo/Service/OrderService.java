package com.MyProject.restdemo.Service;

import java.util.Date;
import java.util.List;

import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.entity.ProductRequest;

public interface OrderService {

	List<Order> findAll();
	
	List<OrderProduct> findAllOrderProduct();
	
	 Order findById(int theId);
	
	Order createOrderForEmployee(OrderRequest orderRequest) throws Exception;
	
	void cancelOrderById(int theId);
}
