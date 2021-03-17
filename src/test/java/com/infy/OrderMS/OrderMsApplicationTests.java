package com.infy.OrderMS;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;
import com.infy.OrderMS.service.OrderService;

@SpringBootTest
public class OrderMsApplicationTests {

	
	@Autowired
	OrderService orderService;
	
	@MockBean
	OrderRepository orderRepository;
	
	@MockBean
	ProductOrderRepository productOrderRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("deprecation")
	OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO(2,1,12345D,new Date(2020,01,28),"HYD","ORDER PLACED");
	ProductsOrder po=new ProductsOrder(1,1,4,1,"ORDER PLACED",12345D);
	List<ProductsOrder> list1= new ArrayList<>();
	List<ProductsOrder> list2= new ArrayList<>();
	@Test
	public void getOrder() {
		Mockito.when(orderRepository.findById(2)).thenReturn(Optional.of(orderDetailsDTO.createEntity()));
		
		int id;
		try {
			id=orderService.getOrderDetails(2).getBuyerId();
		}catch(Exception e) {
			logger.info(e.getMessage());
			id=0;
		}
		assertEquals(1, id);
	}
	
	@Test
	public void getOrderN() {
		Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(orderDetailsDTO.createEntity()));
		
		int id;
		try {
			id=orderService.getOrderDetails(2).getBuyerId();
		}catch(Exception e) {
			id=0;
			logger.info(e.getMessage());
		}
		assertEquals(0, id);
	}
	
	@Test
	public void getProductDetailsByOrderId() {
		list1.add(po);
		Mockito.when(productOrderRepository.findByORDERID(1)).thenReturn(list1);
		int prodId;
		try {
			prodId=orderService.getProductByOrderID(1).get(0).getProdId();
		}catch(Exception e) {
			prodId=0;
			logger.info(e.getMessage());
		}
		
		assertEquals(1,prodId);
	}
	
	@Test
	public void getProductDetailsByOrderIdN() {
		Mockito.when(productOrderRepository.findByORDERID(1)).thenReturn(list1);
		int prodId;
		try {
			prodId=orderService.getProductByOrderID(1).get(0).getProdId();
		}catch(Exception e) {
			prodId=0;
			logger.info(e.getMessage());
		}
		
		assertEquals(0,prodId);
	}
	
	@Test
	public void getProductDetailsBySellerId() {
		list2.add(po);
		Mockito.when(productOrderRepository.findBySELLERID(4)).thenReturn(list2);
		int prodId;
		try {
			prodId=orderService.getProductsBySellerID(4).get(0).getProdId();
		}catch(Exception e) {
			prodId=0;
			logger.info(e.getMessage());
		}
		
		assertEquals(1,prodId);
	}
	
	@Test
	public void getProductDetailsBySellerIdN() {
		Mockito.when(productOrderRepository.findBySELLERID(4)).thenReturn(list2);
		int prodId;
		try {
			prodId=orderService.getProductsBySellerID(4).get(0).getProdId();
		}catch(Exception e) {
			prodId=0;
			logger.info(e.getMessage());
		}
		
		assertEquals(0,prodId);
	}
}
