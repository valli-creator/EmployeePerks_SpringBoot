package com.MyProject.restdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderErrorResponse;
import com.MyProject.restdemo.entity.OrderNotFoundException;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderRestController {

	private OrderService theOrderService;

	public OrderRestController(OrderService theOrderService) {
		super();
		this.theOrderService = theOrderService;
	}
	
	@GetMapping("/orders")
	public List<Order> getAllOrders() {
		 
		return theOrderService.findAll();
	}
	
	@GetMapping("/orders/{orderId}")
	public Order getOrderById(@PathVariable int orderId) {
		Order theOrder = theOrderService.findById(orderId);
		
		if(theOrder==null) {
			throw new OrderNotFoundException("OrderId" + orderId + "not found");
		}
		return theOrder;
		
	}
	
	@PostMapping("/orders")
	public Order createOrders( @Valid @RequestBody OrderRequest theOrderRequest) throws Exception {
		 return theOrderService.createOrderForEmployee(theOrderRequest);
	}

	@DeleteMapping("/orders/{orderId}")
	public String cancelOrderById(@PathVariable int orderId) {
		
		Order theOrder = theOrderService.findById(orderId);
		
		//throw exception if null
		
		if(theOrder ==null)
		{
			throw new OrderNotFoundException("Order id not found "+ orderId);
			
		}
		theOrderService.cancelOrderById(orderId);
		return "Canceled order id- " +orderId;
		
	}
	
	@ExceptionHandler
	public ResponseEntity<OrderErrorResponse> handleException(OrderNotFoundException exc) {
	OrderErrorResponse error = new OrderErrorResponse();
	error.setStatus(HttpStatus.NOT_FOUND.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
}
