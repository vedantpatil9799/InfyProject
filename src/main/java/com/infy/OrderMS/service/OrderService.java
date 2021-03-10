package com.infy.OrderMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.entity.OrderDetails;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;

@Service
public class OrderService {
	
	@Autowired
	ProductOrderRepository repo;
	
	@Autowired
	OrderRepository orderRepository;
	

	public ProductsOrderDTO getProductOrderDetails(CompositeKey compositeKey) {
		ProductsOrderDTO dto=null;
		
		Optional<ProductsOrder> optional=repo.findById(compositeKey);
		if(optional.isPresent()) {
			ProductsOrder order=optional.get();
			dto=ProductsOrderDTO.valueOf(order);
		}
		return dto;
	}
	
	public OrderDTO getOrderDetailByOrderID(Integer orderID) {
		OrderDTO orderDTO=null;
		
		Optional<OrderDetails> optional=orderRepository.findById(orderID);
		if(optional.isPresent()) {
			OrderDetails orderDetails=optional.get();
			List<ProductsOrderDTO> list=new ArrayList<ProductsOrderDTO>();
			List<ProductsOrder> listProdcutsOrder=repo.findByORDERID(orderID);

			for(ProductsOrder productsOrder:listProdcutsOrder) {
				list.add(ProductsOrderDTO.valueOf(productsOrder));
			}
			
			orderDTO=OrderDTO.valueOf(orderDetails, list);
		}
		
		return orderDTO;
	}
	
	public List<ProductsOrderDTO> getProductByOrderID(Integer orderID){
		List<ProductsOrderDTO> list=new ArrayList<ProductsOrderDTO>();
		
		List<ProductsOrder> listProdcutsOrder=repo.findByORDERID(orderID);

		for(ProductsOrder productsOrder:listProdcutsOrder) {
			list.add(ProductsOrderDTO.valueOf(productsOrder));
		}
		
		return list;
	}
}
