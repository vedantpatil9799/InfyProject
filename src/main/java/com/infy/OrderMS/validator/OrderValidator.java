package com.infy.OrderMS.validator;

import java.util.List;

import com.infy.OrderMS.dto.CartDTO;
import com.infy.OrderMS.dto.ProductDTO;
import com.infy.OrderMS.dto.ProductsOrderDTO;

public class OrderValidator {

	public static boolean checkStatus(String status) {
		
		if(status.equalsIgnoreCase("Order Placed")){
			return true;
		}else if(status.equalsIgnoreCase("Packing")) {
			return true;
		}else if(status.equalsIgnoreCase("Dispatched")) {
			return true;
		}else if(status.equalsIgnoreCase("Delivered")) {
			return true;
		}
		return false;
	}
	
	public static boolean checkQuantity(List<ProductDTO> listProductDTO,List<CartDTO> listCartDTO) {
		for(int i=0;i<listCartDTO.size();i++) {
			if(listCartDTO.get(i).getQuantity() > listProductDTO.get(i).getStock()) {
				return false;
			}
		}
		
		return true;
	}

	public static boolean checkReorderQuantity(List<ProductDTO> listProductDTO, List<ProductsOrderDTO> listProductsOrderDTO) {
		for(int i=0;i<listProductDTO.size();i++) {
			if(listProductsOrderDTO.get(i).getQUANTITY() > listProductDTO.get(i).getStock()) {
				return false;
			}
		}
		return true;
	}
	
}
