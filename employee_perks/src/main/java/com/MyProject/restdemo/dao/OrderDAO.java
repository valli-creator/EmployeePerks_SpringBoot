package com.MyProject.restdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;


public interface OrderDAO  {

	List<Order> findAll();
	
	Order findById(int theId);
	
	Order save(Order theOrder);
	
	int findMaxOrderId();
	
	void cancelOrderById(int theId);
	
}
