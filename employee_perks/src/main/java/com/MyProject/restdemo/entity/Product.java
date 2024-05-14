package com.MyProject.restdemo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product {

	//define fields
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="product_id")
		public	int productId;
 
		@Column(name="product_name")
		private String productName;
		
		@Column(name="quantity")
		private int quantity;
		
		@Column(name="points")
		private double points;

		//define constructors
		
		public Product() {
			super();
		}

		public Product(String productName, int quantity, double points) {
			super();
			this.productName = productName;
			this.quantity = quantity;
			this.points = points;
		}
		
		//define getters/setters

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
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

		//define toString
		
		@Override
		public String toString() {
			return "Product [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
					+ ", points=" + points + "]";
		}
		
		

}
