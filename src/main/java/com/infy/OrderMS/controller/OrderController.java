package com.infy.OrderMS.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.dto.PlaceOrderDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.service.OrderService;

@RestController
@CrossOrigin
public class OrderController {

	@Autowired
	OrderService orderService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/orders/reOrder/{orderId}/{prodId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOrderbyProductID(@PathVariable int orderId,@PathVariable int prodId) {
		
		//TODO:complete the flow
		logger.info("Request to reorder product");
		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
	}
	
	
	@PostMapping(value="/orders/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
		
		//TODO:complete the flow
		
		logger.info("Request to place order by buyerID"+placeOrderDTO.getBuyerID());
		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
	}
	
	@GetMapping(value="orders/{orderID}")
	public OrderDTO getOrderDetails(@PathVariable int orderID) {
		
		return orderService.getOrderDetailByOrderID(orderID);
	}
}
