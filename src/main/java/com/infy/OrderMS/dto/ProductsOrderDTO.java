package com.infy.OrderMS.dto;

import com.infy.OrderMS.entity.ProductsOrder;

public class ProductsOrderDTO {
	private	int ORDERID;
	private int PRODID;
	private int SELLERID;
	private int QUANTITY;
	private String STATUS;
	private double price;
	
	
	public ProductsOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductsOrderDTO(int oRDERID, int pRODID, int sELLERID, int qUANTITY, String sTATUS, double price) {
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
	
	public int getPRODID() {
		return PRODID;
	}
	public void setPRODID(int pRODID) {
		PRODID = pRODID;
	}
	public int getSELLERID() {
		return SELLERID;
	}
	public void setORDERID(int oRDERID) {
		ORDERID = oRDERID;
	}
	public void setSELLERID(int sELLERID) {
		SELLERID = sELLERID;
	}

	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
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
		productsOrderDTO.setORDERID(productsOrder.getORDERID());
		productsOrderDTO.setPrice(productsOrder.getPrice());
		productsOrderDTO.setPRODID(productsOrder.getPRODID());
		productsOrderDTO.setQUANTITY(productsOrder.getQUANTITY());
		productsOrderDTO.setSELLERID(productsOrder.getSELLERID());
		productsOrderDTO.setSTATUS(productsOrder.getSTATUS());
		return productsOrderDTO;
	}
	
	public static ProductsOrder saveProductOrder(int orderID,String status, CartDTO cartDTO,ProductDTO productDTO) {
		ProductsOrder productsOrder=new ProductsOrder();
		productsOrder.setORDERID(orderID);
		productsOrder.setPrice(productDTO.getPrice());
		productsOrder.setPRODID(productDTO.getProductId());
		productsOrder.setQUANTITY(cartDTO.getQuantity());
		productsOrder.setSELLERID(productDTO.getSellerId());
		productsOrder.setSTATUS(status);
		
		return productsOrder;
	}
	public static ProductsOrder saveProductOrder(int orderID, ProductsOrderDTO productsOrderDTO) {
		ProductsOrder productsOrder=new ProductsOrder();
		productsOrder.setORDERID(orderID);
		productsOrder.setPrice(productsOrderDTO.getPrice());
		productsOrder.setPRODID(productsOrderDTO.getPRODID());
		productsOrder.setQUANTITY(productsOrderDTO.getQUANTITY());
		productsOrder.setSELLERID(productsOrderDTO.getSELLERID());
		productsOrder.setSTATUS(productsOrderDTO.getSTATUS());
		
		return productsOrder;
	}
}
