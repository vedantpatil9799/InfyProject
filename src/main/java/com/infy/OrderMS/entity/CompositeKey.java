package com.infy.OrderMS.entity;

import java.io.Serializable;


public class CompositeKey implements Serializable{
	private	int ORDERID;
	private int PRODID;
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

}
