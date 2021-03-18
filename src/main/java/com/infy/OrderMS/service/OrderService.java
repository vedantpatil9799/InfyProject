package com.infy.OrderMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infy.OrderMS.dto.BuyerDTO;
import com.infy.OrderMS.dto.CartDTO;
import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.dto.PlaceOrderDTO;
import com.infy.OrderMS.dto.ProductDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.OrderDetails;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;
import com.infy.OrderMS.validator.OrderValidator;

@Service
public class OrderService {
	
	@Autowired
	ProductOrderRepository productOrderRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Value("${userBuyer.uri}")
	String userBuyerUri;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public OrderDetailsDTO getOrderDetails(Integer orderID) {
		OrderDetailsDTO orderDetailsDTO=null;
		Optional<OrderDetails> optional= orderRepository.findById(orderID);
		if(optional.isPresent()) {
			orderDetailsDTO=OrderDetailsDTO.valueOf(optional.get());
		}
		return orderDetailsDTO;
	}
	
	public OrderDTO getOrderDetailByOrderID(Integer orderID) {
		OrderDTO orderDTO=null;
		
		Optional<OrderDetails> optional=orderRepository.findById(orderID);
		if(optional.isPresent()) {
			OrderDetails orderDetails=optional.get();
			orderDTO=OrderDTO.valueOf(orderDetails, getProductByOrderID(orderID));
		}
		
		return orderDTO;
	}
	
	public List<ProductsOrderDTO> getProductByOrderID(Integer orderID){
		List<ProductsOrderDTO> list=new ArrayList<ProductsOrderDTO>();
		
		List<ProductsOrder> listProdcutsOrder=productOrderRepository.findByORDERID(orderID);

		for(ProductsOrder productsOrder:listProdcutsOrder) {
			list.add(ProductsOrderDTO.valueOf(productsOrder));
		}
		
		return list;
	}
	
	public List<ProductsOrderDTO> getProductsBySellerID(Integer sellerID){
		List<ProductsOrderDTO> list=new ArrayList<>();
		List<ProductsOrder> listProdcutsOrder=productOrderRepository.findBySELLERID(sellerID);
		for(ProductsOrder productsOrder:listProdcutsOrder) {
			list.add(ProductsOrderDTO.valueOf(productsOrder));
		}
		
		return list;
	}
	
	
	public Integer placeOrder(PlaceOrderDTO placeOrderDTO,List<ProductDTO> listProductDTO,BuyerDTO buyerDTO,List<CartDTO> listCartDTO) throws Exception {
		
		if(!OrderValidator.checkQuantity(listProductDTO, listCartDTO)) {
			throw new Exception("Products with this much quantity are not available");
		}
		
		//step 4: create order
		OrderDetails order=OrderDetailsDTO.calculateAmount(placeOrderDTO, listProductDTO,buyerDTO,listCartDTO);
		//step:5 save order in orderDetails
		orderRepository.save(order);
		
		//step:6 for each product make entry in productOrdered table
		for(int i=0;i<listCartDTO.size();i++) {
			ProductsOrder product=ProductsOrderDTO.saveProductOrder(order.getORDERID(),
																	order.getSTATUS(),
																	listCartDTO.get(i),
																	listProductDTO.get(i));
			productOrderRepository.save(product);
		
		}
		
		
		return getRewardPoints(buyerDTO.getRewardPoints(), order.getAMOUNT());
	}

	public Integer placeOrder(OrderDetailsDTO orderDetailsDTO, List<ProductsOrderDTO> listProductsOrderDTO,BuyerDTO buyerDTO,List<ProductDTO> listProductDTO) throws Exception {
		
		if(!OrderValidator.checkReorderQuantity(listProductDTO, listProductsOrderDTO)) {
			throw new Exception("Products with this much quantity are not available");
		}
		
		OrderDetails orderDetails=OrderDetailsDTO.calculateAmount(orderDetailsDTO,buyerDTO);
		orderRepository.save(orderDetails);
		
		for(ProductsOrderDTO productsOrderDTO:listProductsOrderDTO) {
			ProductsOrder product=ProductsOrderDTO.saveProductOrder(orderDetails.getORDERID(),productsOrderDTO);
			productOrderRepository.save(product);
		}
		
		return getRewardPoints(buyerDTO.getRewardPoints(), orderDetails.getAMOUNT());
	}
	
	
	public Integer getRewardPoints(Integer rewardPoints,double ammount) {
		if(rewardPoints!=null) {
			Integer newRewardPoints=(int)(ammount/100);
			if(newRewardPoints>rewardPoints) {
				return newRewardPoints-rewardPoints;
			}else {
				return rewardPoints-newRewardPoints;
			}
		}
		return (int) (ammount/100);
	}
	
	
	//get order details by buyerId
	public List<OrderDTO> getOrderDetailsByBuyerID(Integer buyerID){
		List<OrderDTO> list=new ArrayList<>();
			
		List<OrderDetails> listOrderDetails=orderRepository.findByBUYERID(buyerID);
		for(OrderDetails orderDetails:listOrderDetails) {
			OrderDTO orderDTO=OrderDTO.valueOf(orderDetails, getProductByOrderID(orderDetails.getORDERID()));
	
			list.add(orderDTO);
		}
		return list;
	}
	
	public boolean updateStatus(Integer orderId, String status) {
		
		if(!OrderValidator.checkStatus(status)) {
			logger.info("invalid status");
			return false;
		}
		
		Optional<OrderDetails> optional= orderRepository.findById(orderId);
		if(optional.isPresent()) {
			OrderDetails orderDetails=optional.get();
			orderDetails.setSTATUS(status);
			
			orderRepository.save(orderDetails);
			List<ProductsOrder> listProdcutsOrder=productOrderRepository.findByORDERID(orderDetails.getORDERID());

			for(ProductsOrder productsOrder:listProdcutsOrder) {
				productsOrder.setSTATUS(status);
				productOrderRepository.save(productsOrder);
			}
			
			return true;
		}
		
		
		return false;
	}

	public boolean cancelOrder(Integer orderID) {
		
		Optional<OrderDetails> optional= orderRepository.findById(orderID);
		if(optional.isPresent()) {
			OrderDetails orderDetails=optional.get();
			orderRepository.delete(orderDetails);
			
			List<ProductsOrder> listProdcutsOrder=productOrderRepository.findByORDERID(orderDetails.getORDERID());

			for(ProductsOrder productsOrder:listProdcutsOrder) {
				productOrderRepository.delete(productsOrder);
			}
			
			BuyerDTO buyerDTO=new RestTemplate().getForObject(userBuyerUri+orderDetails.getBUYERID(), BuyerDTO.class);
			if(buyerDTO!=null) {
				logger.info("fetching buyer details.."+buyerDTO.getRewardPoints());
				new RestTemplate().put(userBuyerUri+"updateReward/"+orderDetails.getBUYERID()+"/"+getRewardPoints(buyerDTO.getRewardPoints(),orderDetails.getAMOUNT()), null);
				logger.info("updating reward points.."+getRewardPoints(buyerDTO.getRewardPoints(),orderDetails.getAMOUNT()));
			}
			
			return true;
		}
		return false;
	}
}
