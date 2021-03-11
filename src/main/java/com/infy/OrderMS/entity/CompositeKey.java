package com.infy.OrderMS.entity;

import java.io.Serializable;


public class CompositeKey implements Serializable{
	private	long ORDERID;
	private long PRODID;
	public long getORDERID() {
		return ORDERID;
	}
	public void setORDERID(long oRDERID) {
		ORDERID = oRDERID;
	}
	public long getPRODID() {
		return PRODID;
	}
	public void setPRODID(long pRODID) {
		PRODID = pRODID;
	}


}
