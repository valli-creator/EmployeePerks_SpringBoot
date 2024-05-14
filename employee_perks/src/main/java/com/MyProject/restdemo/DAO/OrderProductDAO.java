package com.MyProject.restdemo.DAO;

import java.util.List;
import java.util.Optional;

import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;

public interface OrderProductDAO {

	List<OrderProduct> findAllOrderProduct();
	
	List<OrderProduct> save(List<OrderProduct> theOrderList);
	
}
