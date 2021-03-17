package com.infy.OrderMS.dto;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.OrderMS.entity.OrderDetails;


public class OrderDetailsDTO {
	
	static Logger logger = LoggerFactory.getLogger("OrderDetailsDTO");
	
	private int orderId;
	private int buyerId;
	private double amount;
	private Date date;
	private String address;
	private String status;
	
	
	public OrderDetailsDTO() {
		super();
	}
	
	
	public OrderDetailsDTO(int orderId, int buyerId, double amount, Date date, String address, String status) {
		super();
		this.orderId = orderId;
		this.buyerId = buyerId;
		this.amount = amount;
		this.date = date;
		this.address = address;
		this.status = status;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static OrderDetailsDTO valueOf(OrderDetails orderDetails) {
		OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO();
		orderDetailsDTO.setAddress(orderDetails.getADDRESS());
		orderDetailsDTO.setAmount(orderDetails.getAMOUNT());
		orderDetailsDTO.setBuyerId(orderDetails.getBUYERID());
		orderDetailsDTO.setDate(orderDetails.getDate());
		orderDetailsDTO.setOrderId(orderDetails.getORDERID());
		orderDetailsDTO.setStatus(orderDetails.getSTATUS());
		return orderDetailsDTO;
	}
	
	public OrderDetails createEntity() {
		OrderDetails orderDetails=new OrderDetails();
		orderDetails.setBUYERID(this.getBuyerId());
		orderDetails.setADDRESS(this.getAddress());
		orderDetails.setAMOUNT(this.getAmount());
		orderDetails.setDate(this.getDate());
		orderDetails.setORDERID(this.getOrderId());
		orderDetails.setSTATUS(this.getStatus());
		
		return orderDetails;
	}
	
	public static OrderDetails calculateAmount(PlaceOrderDTO placeOrderDTO,
												List<ProductDTO> listProductDTO,
												BuyerDTO buyerDTO,
												List<CartDTO> listCartDTO) {
		
		OrderDetails orderDetails=new OrderDetails();
		
		orderDetails.setBUYERID(placeOrderDTO.getBuyerID());
		orderDetails.setADDRESS(placeOrderDTO.getAddress());
		
		double discount=0;
		if(buyerDTO.getRewardPoints()!=null) {
			discount=buyerDTO.getRewardPoints()/4.0;
		}
		logger.info("discount applied for order is: "+discount);
		
		double amount=0;
		for(int i=0;i<listCartDTO.size();i++) {
			amount+=listCartDTO.get(i).getQuantity()*listProductDTO.get(i).getPrice();
		}
		
		orderDetails.setAMOUNT(amount-discount);
		
		orderDetails.setDate(new Date(System.currentTimeMillis()));
		orderDetails.setSTATUS("Order Placed");
		
		return orderDetails;
	}
	
	public static OrderDetails calculateAmount(OrderDetailsDTO orderDetailsDTO, BuyerDTO buyerDTO) {
		
		double discount=0;
		if(buyerDTO.getRewardPoints()!=null) {
			discount=buyerDTO.getRewardPoints()/4;
		}
		logger.info("discount applied for order is: "+discount);
		
		OrderDetails orderDetails=new OrderDetails();
		orderDetails.setBUYERID(orderDetailsDTO.getBuyerId());
		orderDetails.setADDRESS(orderDetailsDTO.getAddress());
		orderDetails.setAMOUNT(orderDetailsDTO.getAmount()-discount);
		orderDetails.setDate(new Date(System.currentTimeMillis()));
		orderDetails.setSTATUS(orderDetailsDTO.getStatus());
		
		return orderDetails;
	}
}
