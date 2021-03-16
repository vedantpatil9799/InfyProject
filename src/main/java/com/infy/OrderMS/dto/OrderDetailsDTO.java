package com.infy.OrderMS.dto;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.OrderMS.entity.OrderDetails;


public class OrderDetailsDTO {
	
	static Logger logger = LoggerFactory.getLogger("OrderDetailsDTO");
	
	private int ORDERID;
	private int BUYERID;
	private double AMOUNT;
	private Date date;
	private String ADDRESS;
	private String STATUS;
	
	
	public OrderDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDetailsDTO(int oRDERID, int bUYERID, double aMOUNT, Date date, String aDDRESS, String sTATUS) {
		super();
		ORDERID = oRDERID;
		BUYERID = bUYERID;
		AMOUNT = aMOUNT;
		this.date = date;
		ADDRESS = aDDRESS;
		STATUS = sTATUS;
	}
	public int getORDERID() {
		return ORDERID;
	}
	public void setORDERID(int oRDERID) {
		ORDERID = oRDERID;
	}
	public int getBUYERID() {
		return BUYERID;
	}
	public void setBUYERID(int bUYERID) {
		BUYERID = bUYERID;
	}
	public double getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(double aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public static OrderDetailsDTO valueOf(OrderDetails orderDetails) {
		OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO();
		orderDetailsDTO.setADDRESS(orderDetails.getADDRESS());
		orderDetailsDTO.setAMOUNT(orderDetails.getAMOUNT());
		orderDetailsDTO.setBUYERID(orderDetails.getBUYERID());
		orderDetailsDTO.setDate(orderDetails.getDate());
		orderDetailsDTO.setORDERID(orderDetails.getORDERID());
		orderDetailsDTO.setSTATUS(orderDetails.getSTATUS());
		return orderDetailsDTO;
	}
	
	public OrderDetails createEntity() {
		OrderDetails orderDetails=new OrderDetails();
		orderDetails.setBUYERID(this.getBUYERID());
		orderDetails.setADDRESS(this.getADDRESS());
		orderDetails.setAMOUNT(this.getAMOUNT());
		orderDetails.setDate(this.getDate());
		orderDetails.setORDERID(this.getORDERID());
		orderDetails.setSTATUS(this.getSTATUS());
		
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
			discount=buyerDTO.getRewardPoints()/4;
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
		orderDetails.setBUYERID(orderDetailsDTO.getBUYERID());
		orderDetails.setADDRESS(orderDetailsDTO.getADDRESS());
		orderDetails.setAMOUNT(orderDetailsDTO.getAMOUNT()-discount);
		orderDetails.setDate(new Date(System.currentTimeMillis()));
		orderDetails.setSTATUS(orderDetailsDTO.getSTATUS());
		
		return orderDetails;
	}
}
