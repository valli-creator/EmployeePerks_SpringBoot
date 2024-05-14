package com.MyProject.restdemo.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
//@IdClass(OrderId.class)
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@Column(name="points")
	private double totalPoints;
	
	@Column(name="status")
	private String status;
	
	@JsonManagedReference
	@OneToMany(mappedBy ="order" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderProduct> orderProduct;
	
	
	public Order() {
		super();
	}
	
	public Order(Date date, double totalPoints, String status) {
		super();
		this.date = date;
		this.totalPoints = totalPoints;
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	

	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public List<OrderProduct> getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}

	@Override
	public String toString() {
		return "Order [date=" + date + ", employeeId=" + employeeId + ", totalPoints=" + totalPoints + ", status=" + status + "]";
	}

}
