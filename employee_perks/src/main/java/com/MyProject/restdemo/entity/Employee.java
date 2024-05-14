package com.MyProject.restdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {

	//define fields
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id")
		private int id;
		
		@Column(name="first_name")
		private String firstName;
		
		@Column(name="last_name")
		private String lastName;
		
		@Column(name="email")
		private String email;
		
		@Column(name="credit_points")
		private double creditPoints;

		//define constructors
		
		public Employee() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public Employee(String firstName, String lastName, String email,double creditPoints) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.creditPoints = creditPoints;
			
		}

		//define getter/setter
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		
		
		public double getCreditPoints() {
			return creditPoints;
		}

		public void setCreditPoints(double creditPoints) {
			this.creditPoints = creditPoints;
		}

		//define toString
		
		@Override
		public String toString() {
			return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
					+ ", creditPoints=" + creditPoints + "]";
		}

		
		
		
}
