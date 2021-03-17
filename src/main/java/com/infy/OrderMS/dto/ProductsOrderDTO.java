package com.infy.OrderMS.dto;

import com.infy.OrderMS.entity.ProductsOrder;

public class ProductsOrderDTO {
	private	int orderId;
	private int prodId;
	private int sellerId;
	private int quantity;
	private String status;
	private double price;
	
	
	public ProductsOrderDTO() {
		super();
	}
	
	public ProductsOrderDTO(int orderId, int prodId, int sellerId, int quantity, String status, double price) {
		super();
		this.orderId = orderId;
		this.prodId = prodId;
		this.sellerId = sellerId;
		this.quantity = quantity;
		this.status = status;
		this.price = price;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public static ProductsOrderDTO valueOf(ProductsOrder productsOrder) {
		ProductsOrderDTO productsOrderDTO=new ProductsOrderDTO();
		productsOrderDTO.setOrderId(productsOrder.getORDERID());
		productsOrderDTO.setPrice(productsOrder.getPrice());
		productsOrderDTO.setProdId(productsOrder.getPRODID());
		productsOrderDTO.setQuantity(productsOrder.getQUANTITY());
		productsOrderDTO.setSellerId(productsOrder.getSELLERID());
		productsOrderDTO.setStatus(productsOrder.getSTATUS());
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
		productsOrder.setPRODID(productsOrderDTO.getProdId());
		productsOrder.setQUANTITY(productsOrderDTO.getQuantity());
		productsOrder.setSELLERID(productsOrderDTO.getSellerId());
		productsOrder.setSTATUS(productsOrderDTO.getStatus());
		
		return productsOrder;
	}
}
