package com.infy.OrderMS.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.infy.OrderMS.dto.BuyerDTO;
import com.infy.OrderMS.dto.CartDTO;
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
	
	@Value("${product.uri}")
	String productUri;
	
	@Value("${userCart.uri}")
	String userCartUri;
	
	@Value("${userBuyer.uri}")
	String userBuyerUri;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value="/{orderID}")
	public OrderDTO getOrderDetails(@PathVariable int orderID) {
		
		return orderService.getOrderDetailByOrderID(orderID);
	}
	
	@GetMapping(value="/seller/{sellerID}")
	public List<ProductsOrderDTO> getOrderDetailsBySellerID(@PathVariable int sellerID){
		return orderService.getProductsBySellerID(sellerID);
	}
	
	@PostMapping(value="/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
		
		logger.info("Request to place order by buyerID: "+placeOrderDTO.getBuyerID());
		try {
			//step:1 fetch details from cart UserMS
			logger.info("Fetching cart details....");
			CartDTO[] object=new RestTemplate().getForObject(userCartUri+"getAll/"+placeOrderDTO.getBuyerID(), CartDTO[].class);
			List<CartDTO> listCartDTO=Arrays.asList(object);	
			
			if(listCartDTO == null || listCartDTO.isEmpty()) {
				return new ResponseEntity<String>("Something went wrong, please try again later.",HttpStatus.OK);
			}
			//step:2 fetch buyer details from userMS
			logger.info("fetching buyer details....");
			BuyerDTO buyerDTO=new RestTemplate().getForObject(userBuyerUri+"get/"+placeOrderDTO.getBuyerID(), BuyerDTO.class);
				
			//step:3 fetch product details from productMS
			logger.info("fetching product details....");
			List<ProductDTO> listProductDTO=new ArrayList<ProductDTO>();
			for(CartDTO cartDTO:listCartDTO) {
				ProductDTO productDTO=new RestTemplate().getForObject(productUri+"get/"+cartDTO.getProdId(), ProductDTO.class);
				listProductDTO.add(productDTO);
			}
			//step:4,5,6 calculate discounts and create the order
			logger.info("Calculating, creating order for product....");
			Integer rewardPoints=orderService.placeOrder(placeOrderDTO, listProductDTO,buyerDTO,listCartDTO);
			
			//step7: update reward points
			logger.info("Updating reward points....");
			new RestTemplate().put(userBuyerUri+"updateReward/"+placeOrderDTO.getBuyerID()+"/"+rewardPoints, null);
			
			//step8: remove product from cart
			logger.info("Removing products from cart....");
			//step9: update stock of all product 
			logger.info("Updating stock of products....");
			for(CartDTO cartDTO:listCartDTO) {
			
				new RestTemplate().delete(userCartUri+"delete/"+cartDTO.getBuyerId()+"/"+cartDTO.getProdId());
				String response=new RestTemplate().getForObject(productUri+"updateStock/"+cartDTO.getProdId()+"/"+cartDTO.getQuantity(),String.class);
			}
		}catch(Exception e) {
			logger.info(e.getMessage());
			return new ResponseEntity<String>("Something went wrong, please try again later.",HttpStatus.OK);
		}

		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
	}
	
	
	@PostMapping(value="/reOrder/{orderId}/{buyerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reorderProduct(@PathVariable int orderId,@PathVariable int buyerId) {
		
		logger.info("Request to reorder product {OrderID: "+orderId+",BuyerID: "+buyerId+"}");
		
		try {
			//step1: fetch the previous order details
			OrderDetailsDTO orderDetailsDTO=orderService.getOrderDetails(orderId);
			
			//step2: validate buyerid
			if(orderDetailsDTO==null || orderDetailsDTO.getBUYERID()!= buyerId) {
				return new ResponseEntity<String>("Their is no order placed for this {OrderID: "+orderId+",BuyerID: "+buyerId+"}",HttpStatus.BAD_REQUEST);
			}
			
			//step3: fetch previously ordered products
			List<ProductsOrderDTO> listProductsOrderDTO=orderService.getProductByOrderID(orderId);
			
			//step4: fetch buyer details
			logger.info("fetching buyer details....");
			BuyerDTO buyerDTO=new RestTemplate().getForObject(userBuyerUri+"get/"+buyerId, BuyerDTO.class);
			
			//step5: place order
			Integer rewardPoints=orderService.placeOrder(orderDetailsDTO,listProductsOrderDTO,buyerDTO);
			
			//step6: update reward points
			logger.info("Updating reward points....");
			new RestTemplate().put(userBuyerUri+"updateReward/"+buyerId+"/"+rewardPoints, null);
			
			//step7: update stock of all product 
			logger.info("Updating stock of products....");
			for(ProductsOrderDTO productsOrderDTO:listProductsOrderDTO) {
				String response=new RestTemplate().getForObject(productUri+"updateStock/"+productsOrderDTO.getPRODID()+"/"+productsOrderDTO.getQUANTITY(),String.class);
				logger.info(response);
			}
		}catch(Exception e) {
			logger.info(e.getMessage());
			return new ResponseEntity<String>("Something went wrong, please try again later.",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.OK);
	}
}
