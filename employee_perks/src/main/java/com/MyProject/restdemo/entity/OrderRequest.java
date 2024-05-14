package com.MyProject.restdemo.entity;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Validated
public class OrderRequest {

	//to get order request from user as Json format
	
	private int orderId;
	
	@NotNull(message = "empId is Required")
	private int empId;
	
	private List<ProductRequest> products;
	

	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRequest(int empId, List<ProductRequest> products) {
		super();
		this.empId = empId;
		this.products = products;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public List<ProductRequest> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequest> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "OrderRequest [orderId=" + orderId + ", empId=" + empId + ", products=" + products + "]";
	}
	
	
}
