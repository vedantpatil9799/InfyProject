package com.infy.OrderMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.OrderMS.dto.BuyerDTO;
import com.infy.OrderMS.dto.CartDTO;
import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.dto.PlaceOrderDTO;
import com.infy.OrderMS.dto.ProductDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.entity.OrderDetails;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;

@Service
public class OrderService {
	
	@Autowired
	ProductOrderRepository productOrderRepository;
	
	@Autowired
	OrderRepository orderRepository;
	

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
			List<ProductsOrderDTO> list=new ArrayList<ProductsOrderDTO>();
			List<ProductsOrder> listProdcutsOrder=productOrderRepository.findByORDERID(orderID);

			for(ProductsOrder productsOrder:listProdcutsOrder) {
				list.add(ProductsOrderDTO.valueOf(productsOrder));
			}
			
			orderDTO=OrderDTO.valueOf(orderDetails, list);
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
		List<ProductsOrderDTO> list=new ArrayList<ProductsOrderDTO>();
		List<ProductsOrder> listProdcutsOrder=productOrderRepository.findBySELLERID(sellerID);
		for(ProductsOrder productsOrder:listProdcutsOrder) {
			list.add(ProductsOrderDTO.valueOf(productsOrder));
		}
		
		return list;
	}
	
	public Integer placeOrder(PlaceOrderDTO placeOrderDTO,List<ProductDTO> listProductDTO,BuyerDTO buyerDTO,List<CartDTO> listCartDTO) {
		
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

	public Integer placeOrder(OrderDetailsDTO orderDetailsDTO, List<ProductsOrderDTO> listProductsOrderDTO,BuyerDTO buyerDTO) {
		
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
}
