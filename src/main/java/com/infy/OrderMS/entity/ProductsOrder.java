package com.infy.OrderMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="productsordered")
@IdClass(CompositeKey.class)
public class ProductsOrder {
	
	@Id
	private	int ORDERID;
	@Id
	private int PRODID;
	@Column(nullable = false)
	private int SELLERID;
	@Column(nullable = false)
	private int QUANTITY;
	@Column(nullable = false)
	private String STATUS;
	private double price;
	
	

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

	public void setSELLERID(int sELLERID) {
		SELLERID = sELLERID;
	}


	public void setORDERID(int oRDERID) {
		ORDERID = oRDERID;
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
	public ProductsOrder(int oRDERID, int pRODID, int sELLERID, int qUANTITY, String sTATUS, double price) {
		super();
		ORDERID = oRDERID;
		PRODID = pRODID;
		SELLERID = sELLERID;
		QUANTITY = qUANTITY;
		STATUS = sTATUS;
		this.price = price;
	}
	public ProductsOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
