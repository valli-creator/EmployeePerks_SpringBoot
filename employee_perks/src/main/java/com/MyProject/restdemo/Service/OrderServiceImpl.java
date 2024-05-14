package com.MyProject.restdemo.Service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.MyProject.restdemo.DAO.OrderDAO;
import com.MyProject.restdemo.DAO.OrderProductDAO;
import com.MyProject.restdemo.DAO.ProductDAO;
import com.MyProject.restdemo.entity.Employee;
import com.MyProject.restdemo.entity.Order;
import com.MyProject.restdemo.entity.OrderNotFoundException;
import com.MyProject.restdemo.entity.OrderProduct;
import com.MyProject.restdemo.entity.OrderRequest;
import com.MyProject.restdemo.entity.Product;
import com.MyProject.restdemo.entity.ProductRequest;


import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderDAO theOrderDAO;
	private OrderProductDAO theOrderProductDAO;
	private ProductDAO theProductDAO;
	private EmployeeService theEmployeeService;
	
	private int maxOrderId;
	
	@Autowired
	public OrderServiceImpl(OrderDAO theOrderDAO,OrderProductDAO theOrderProductDAO,
							ProductDAO theProductDAO,EmployeeService theEmployeeService) {
		super();
		this.theOrderDAO = theOrderDAO;
		this.theOrderProductDAO = theOrderProductDAO;
		this.theProductDAO = theProductDAO;
		this.theEmployeeService  = theEmployeeService;
		
	}

	@Override
	public List<Order> findAll() {
		
		return theOrderDAO.findAll();
	}

	@Override
    public Order findById(int theId) {
        Optional<Order> result = Optional.ofNullable(theOrderDAO.findById(theId));

        Order theOrder = null;

        if (result.isPresent()) {
            theOrder = result.get();
        }
        else {
           throw new OrderNotFoundException("Did not find Order id - " + theId);
        }
        return theOrder;
    }

	private Order createOrderFromProduct(int empId,String status) {
		Order theOrder =new Order();
		java.util.Date currentDate = new java.util.Date();
		theOrder.setDate(new Date(currentDate.getTime()));		
		theOrder.setEmployeeId(empId);
		theOrder.setStatus(status);
	
		return theOrder;
	}
	
	private OrderProduct createOrderProduct(Order order, ProductRequest product,double points) {
		OrderProduct theOrderProduct = new OrderProduct();
		theOrderProduct.setProductId(product.getProductId());
		theOrderProduct.setQuantity(product.getQuantity());
		theOrderProduct.setPoints(points * (product.getQuantity()));
		theOrderProduct.setOrder(order);
		
		return theOrderProduct;
	}
	
	@Override
	@Transactional
	public Order createOrderForEmployee(OrderRequest orderRequest) throws Exception{
		String status = "Deducted";
		double totalPoints = 0;
		Employee theEmployee = theEmployeeService.findById(orderRequest.getEmpId());
		Order order = 
				this.createOrderFromProduct(orderRequest.getEmpId(),status);
		List<OrderProduct> orderProductList = new ArrayList();
		for(ProductRequest product : orderRequest.getProducts()) {
		Product dbProduct = theProductDAO.findById(product.getProductId()) ;
		if(dbProduct.getQuantity() < product.getQuantity() ) {
			throw new BadRequestException("Insufficient Quantity");
		}
			
		double thePoints = getPointsForProduct(product);
		orderProductList.add(this.createOrderProduct(order,product,thePoints));
		totalPoints = totalPoints + (thePoints * product.getQuantity());
		}
		order.setTotalPoints(totalPoints);
		order.setOrderProduct(orderProductList);	
		theOrderDAO.save(order);
		Pair<Double,String> pair = deductPointsForEmployee(theEmployee,totalPoints);
		double deductedPoints = pair.getFirst();
		 status = pair.getSecond();
		theEmployee.setCreditPoints(deductedPoints);
		order.setStatus(status);
		theEmployeeService.save(theEmployee);
	
			return order;
	
	}
	
	public Pair<Double,String> deductPointsForEmployee(Employee theEmployee,double totalPoints) throws Exception {
		double deductPoints =0;
		String status = "";
		double creditPoints = theEmployee.getCreditPoints();
		if(creditPoints >= totalPoints) {
			deductPoints = creditPoints - totalPoints;
			status = "Deducted";
		}
		else {
			System.out.println("Insufficient Points");
			deductPoints = creditPoints;
			status = "Declined";
			throw new BadRequestException("Insufficient Points");
		}
		return Pair.of(deductPoints, status);
		
	}
	
	
	public double getPointsForProduct(ProductRequest product) {
		Product theProduct = theProductDAO.findById(product.getProductId());
		return theProduct.getPoints();		
	}


	@Override
	@Transactional
	public void cancelOrderById(int theId) {
		theOrderDAO.cancelOrderById(theId);
	}

	@Override
	public List<OrderProduct> findAllOrderProduct() {
		
		return theOrderProductDAO.findAllOrderProduct();
	}

}
