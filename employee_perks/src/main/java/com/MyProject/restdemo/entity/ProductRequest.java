package com.MyProject.restdemo.entity;

import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

public class ProductRequest {

	private int productId;
	
	private int quantity;
	
	private double points;

	public ProductRequest() {
		super();
	}

	public ProductRequest(int productId, int quantity,double points) {
		super();
		this.productId = productId;
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return "ProductRequest [productId=" + productId + ", quantity=" + quantity + ", points=" + points + "]";
	}

	
	
	
}
