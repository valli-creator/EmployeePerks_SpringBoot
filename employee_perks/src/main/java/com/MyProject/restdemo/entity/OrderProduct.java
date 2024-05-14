package com.MyProject.restdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orderproduct")
public class OrderProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderproduct_id")
	private int orderProductId;

	@JsonBackReference
	@ManyToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="order_id")
	private Order order;
	

	@Column(name="product_id")
	private int productId;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="points")
	private double points;
	

	public OrderProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderProduct(double points) {
		super();
		this.points = points;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	

	public int getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(int orderProductId) {
		this.orderProductId = orderProductId;
	}
	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public int getOrderId() {
		int orderId = order.getOrderId();
		return orderId;
	}

	@Override
	public String toString() {
		return "OrderProduct [orderProductId=" + orderProductId + ", orderId=" + order.getOrderId() + ", productId=" + productId
				+ ", quantity=" + quantity + ", points=" + points + "]";
	}

	
	
}
