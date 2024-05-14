package com.MyProject.restdemo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class OrderProductDAOImpl implements OrderProductDAO {

	private EntityManager theEntityManager;
	
	@Autowired
	public OrderProductDAOImpl(EntityManager theEntityManager) {
		super();
		this.theEntityManager = theEntityManager;
	}

	@Override
	public List<OrderProduct> findAllOrderProduct() {
		TypedQuery<OrderProduct> theQuery = theEntityManager.createQuery("from OrderProduct", OrderProduct.class);
		
		List<OrderProduct> orderProduct = new ArrayList<>();
		
		orderProduct = theQuery.getResultList();
		return orderProduct;
	}
	
	@Override
	public List<OrderProduct> save(List<OrderProduct> theOrderList) {
		Optional.ofNullable(theOrderList).ifPresent(list -> {
			list.stream().forEach(order -> theEntityManager.persist(order));
		});
		return theOrderList;
	}

}
