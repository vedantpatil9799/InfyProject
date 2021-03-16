package com.infy.OrderMS.dto;

public class CartDTO {
	private int buyerId;
    private int prodId;
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
