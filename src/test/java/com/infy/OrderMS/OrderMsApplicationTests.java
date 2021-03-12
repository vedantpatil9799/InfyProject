package com.infy.OrderMS;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;
import com.infy.OrderMS.service.OrderService;

@SpringBootTest
public class OrderMsApplicationTests {

	@Autowired
	OrderService orderSerice;
	
	@MockBean
	OrderRepository orderRepository;
	
	@MockBean
	ProductOrderRepository productOrderRepository;
	
	OrderDTO orderDTO=new OrderDTO();
	OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO(1,11,12345D,new Date(2020,01,28),"HYD","ORDER PLACED");
	
	ProductsOrder po1=new ProductsOrder(1,1,4,1,"ORDER PLACED",12345D);
	ProductsOrder po2=new ProductsOrder(1,2,4,1,"ORDER PLACED",999.00);
	List<ProductsOrder> list= new ArrayList<ProductsOrder>(List.of(po1,po2));
	
	
	@Test
	public void getOrdersByOrderID() {
		Mockito.when(productOrderRepository.findByORDERID(1)).thenReturn(list);
		
		long message;
		try {
			message=orderSerice.getProductByOrderID(1).get(0).getORDERID();
		}
		catch(Exception e) {
			message=0;//e.getMessage();
		}
		assertEquals(1, message);
	}
	
	@Test
	public void getOrdersBySellerID() {
		Mockito.when(productOrderRepository.findByORDERID(4)).thenReturn(list);
		
		long message;
		try {
			message=orderSerice.getProductByOrderID(1).get(0).getORDERID();
		}
		catch(Exception e) {
			message=0;//e.getMessage();
		}
		assertEquals(1, message);
	}
}
