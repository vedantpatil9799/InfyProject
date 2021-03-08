package com.infy.OrderMS.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="productsordered")
public class ProductsOrder {
	
	@EmbeddedId
	private CompositeKey compositeKey;
	@Column(nullable = false)
	private int SELLERID;
	@Column(nullable = false)
	private String QUANTITY;
	@Column(nullable = false)
	private String STATUS;
	private double price;
	
	/*
	 * public int getORDERID() { return ORDERID; } public void setORDERID(int
	 * oRDERID) { ORDERID = oRDERID; } public int getPRODID() { return PRODID; }
	 * public void setPRODID(int pRODID) { PRODID = pRODID; }
	 */
	public CompositeKey getCompositeKey() {
		return compositeKey;
	}
	public void setCompositeKey(CompositeKey compositeKey) {
		this.compositeKey = compositeKey;
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
	
	
}
