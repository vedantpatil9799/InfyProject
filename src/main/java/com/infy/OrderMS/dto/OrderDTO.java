package com.infy.OrderMS.dto;

import java.util.Date;
import java.util.List;

import com.infy.OrderMS.entity.OrderDetails;

public class OrderDTO {
	private int ORDERID;
	private int BUYERID;
	private double AMOUNT;
	private Date date;
	private String ADDRESS;
	private String STATUS;
	private List<ProductsOrderDTO> orderedProducts;
	
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

	public List<ProductsOrderDTO> getOrderedProducts() {
		return orderedProducts;
	}
	public void setOrderedProducts(List<ProductsOrderDTO> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}
	
	public static OrderDTO valueOf(OrderDetails orderDetails,List<ProductsOrderDTO> list) {
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setORDERID(orderDetails.getORDERID());
		orderDTO.setBUYERID(orderDetails.getBUYERID());
		orderDTO.setAMOUNT(orderDetails.getAMOUNT());
		orderDTO.setDate(orderDetails.getDate());
		orderDTO.setADDRESS(orderDetails.getADDRESS());
		orderDTO.setSTATUS(orderDetails.getSTATUS());
		orderDTO.setOrderedProducts(list);
		return orderDTO;
	}
	public OrderDTO(int oRDERID, int bUYERID, double aMOUNT, Date date, String aDDRESS, String sTATUS,List<ProductsOrderDTO> orderedProducts) {
		super();
		ORDERID = oRDERID;
		BUYERID = bUYERID;
		AMOUNT = aMOUNT;
		this.date = date;
		ADDRESS = aDDRESS;
		STATUS = sTATUS;
		this.orderedProducts = orderedProducts;
	}
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
