package com.infy.OrderMS.dto;

public class PlaceOrderDTO {
	private String address;
	private int buyerID;
	private int productID;
	private int quantity;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
