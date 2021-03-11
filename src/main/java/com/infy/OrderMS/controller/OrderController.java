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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.dto.PlaceOrderDTO;
import com.infy.OrderMS.dto.ProductDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping(value="/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/reOrder/{orderId}/{prodId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOrderbyProductID(@PathVariable int orderId,@PathVariable int prodId) {
		
		//TODO:complete the flow
		logger.info("Request to reorder product");
		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
	}
	
	
	@PostMapping(value="/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
		
		logger.info("Request to place order by buyerID"+placeOrderDTO.getBuyerID());
		
		ProductDTO productDTO=new RestTemplate().getForObject("http://localhost:9001/product/get/"+placeOrderDTO.getProductID(), ProductDTO.class);
		
		if(orderService.placeOrder(placeOrderDTO, productDTO)) {
			String response=new RestTemplate().getForObject("http://localhost:9001/product/updateStock/"+placeOrderDTO.getProductID()+"/"+placeOrderDTO.getQuantity(),
															 String.class);
			
			return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Something went wrong, please try again later.",HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/{orderID}")
	public OrderDTO getOrderDetails(@PathVariable int orderID) {
		
		return orderService.getOrderDetailByOrderID(orderID);
	}
	
	@GetMapping(value="/seller/{sellerID}")
	public List<ProductsOrderDTO> getOrderDetailsBySellerID(@PathVariable int sellerID){
		return orderService.getProductsBySellerID(sellerID);
	}
}
