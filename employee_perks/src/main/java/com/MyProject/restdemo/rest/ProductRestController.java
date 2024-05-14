package com.MyProject.restdemo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyProject.restdemo.Service.ProductService;
import com.MyProject.restdemo.entity.Product;

@RestController
@RequestMapping("/api")
public class ProductRestController {

	private ProductService productService;

	public ProductRestController(ProductService theProductService) {
		productService = theProductService;
		
	}
	
	@GetMapping("/products")
	public List<Product> findAll() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{productId}")
	public Product findById(@PathVariable int productId) {
		
		Product product = productService.getProductById(productId);
		
		if(product == null) {
			throw new RuntimeException("Product id not Found " + productId);
			
		}
		
		return product;
	}
	
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product theProduct) {
		
		Product dbProduct = productService.save(theProduct);
		return dbProduct;
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product theProduct) {
		Product dbProduct = productService.save(theProduct);
		return dbProduct;
	}
	
	@DeleteMapping("/products/{productId}")
	public String deleteProduct(@PathVariable int productId) {
		Product product = productService.getProductById(productId);
		return "Deleted product id " +productId;
	}
}
