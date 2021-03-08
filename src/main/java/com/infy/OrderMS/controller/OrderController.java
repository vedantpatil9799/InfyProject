package com.infy.OrderMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.service.OrderService;

@RestController
@CrossOrigin
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping(value="/orders/{orderId}/{prodId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductsOrderDTO getOrderbyProductID(@PathVariable int orderId,@PathVariable int prodId) {
		
		CompositeKey compositeKey=new CompositeKey();
		compositeKey.setORDERID(orderId);
		compositeKey.setPRODID(prodId);
		ProductsOrderDTO dto=orderService.getProductOrderDetails(compositeKey);
		
		return dto;
	}
}
