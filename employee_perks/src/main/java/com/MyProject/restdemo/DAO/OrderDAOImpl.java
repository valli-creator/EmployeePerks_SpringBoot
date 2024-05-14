package com.MyProject.restdemo.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class OrderDAOImpl implements OrderDAO {

	private EntityManager theEntityManager;
	
	
	public OrderDAOImpl(EntityManager theEntityManager) {
		this.theEntityManager = theEntityManager;
	}

	@Override
	public List<Order> findAll() {
		TypedQuery<Order> theQuery = theEntityManager.createQuery("from Order", Order.class);
		
		List<Order> orders = new ArrayList<>();
		
		orders = theQuery.getResultList();
		return orders;
	}

	@Override
	public Order findById(int theId) {
		Order theOrder = theEntityManager.find(Order.class, theId);
		return theOrder;
	}
	
	@Override
	public int findMaxOrderId() {
		TypedQuery<Integer> theQuery = theEntityManager.createQuery("Select max(orderId) from Order",Integer.class);
		int result = theQuery.getSingleResult().intValue();
		return result;
	}

	@Override
	public void cancelOrderById(int theId) {
		Order theOrder = theEntityManager.find(Order.class, theId);
		theEntityManager.remove(theOrder);
	}

	@Override
	public Order save(Order theOrder) {
	 theEntityManager.persist(theOrder);
		return theOrder;
	}
	
}
