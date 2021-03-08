package com.infy.OrderMS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.OrderMS.dto.ProductsOrderDTO;
import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.ProductOrderRepository;

@Service
public class OrderService {
	
	@Autowired
	ProductOrderRepository repo;
	
	public ProductsOrderDTO getProductOrderDetails(CompositeKey compositeKey) {
		ProductsOrderDTO dto=null;
		
		Optional<ProductsOrder> optional=repo.findById(compositeKey);
		if(optional.isPresent()) {
			ProductsOrder order=optional.get();
			dto=ProductsOrderDTO.valueOf(order);
		}
		return dto;
	}
}
