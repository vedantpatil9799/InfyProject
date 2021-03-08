package com.infy.OrderMS.dto;

import com.infy.OrderMS.entity.OrderDetails;
import com.infy.OrderMS.entity.ProductsOrder;

public class ProductsOrderDTO {
	private	int ORDERID;
	private int PRODID;
	private int SELLERID;
	private String QUANTITY;
	private String STATUS;
	private double price;
	
	
	public ProductsOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductsOrderDTO(int oRDERID, int pRODID, int sELLERID, String qUANTITY, String sTATUS, double price) {
		super();
		ORDERID = oRDERID;
		PRODID = pRODID;
		SELLERID = sELLERID;
		QUANTITY = qUANTITY;
		STATUS = sTATUS;
		this.price = price;
	}
	public int getORDERID() {
		return ORDERID;
	}
	public void setORDERID(int oRDERID) {
		ORDERID = oRDERID;
	}
	public int getPRODID() {
		return PRODID;
	}
	public void setPRODID(int pRODID) {
		PRODID = pRODID;
	}
	public int getSELLERID() {
		return SELLERID;
	}
	public void setSELLERID(int sELLERID) {
		SELLERID = sELLERID;
	}
	public String getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public static ProductsOrderDTO valueOf(ProductsOrder productsOrder) {
		ProductsOrderDTO productsOrderDTO=new ProductsOrderDTO();
		productsOrderDTO.setORDERID(productsOrder.getCompositeKey().getORDERID());
		productsOrderDTO.setPrice(productsOrder.getPrice());
		productsOrderDTO.setPRODID(productsOrder.getCompositeKey().getPRODID());
		productsOrderDTO.setQUANTITY(productsOrder.getQUANTITY());
		productsOrderDTO.setSELLERID(productsOrder.getSELLERID());
		productsOrderDTO.setSTATUS(productsOrder.getSTATUS());
		return productsOrderDTO;
	}
	
}
