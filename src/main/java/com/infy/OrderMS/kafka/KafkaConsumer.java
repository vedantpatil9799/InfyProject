package com.infy.OrderMS.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.infy.OrderMS.dto.CartDTO;

@Component
public class KafkaConsumer {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${userCart.uri}")
	String userCartUri;
	
	@KafkaListener(topics = "Cart", groupId = "id", containerFactory = "cartListener")
    public void consume(String msg)
    {
		logger.info("message = " + msg);
		msg=msg.replace("<", "");
		msg=msg.replace(">", "#");
		String[] splitMsg=msg.split("#");
		
		logger.info("BuyerId: " + splitMsg[0] + "ProdId: "+ splitMsg[1] + "quantity: " + splitMsg[2]);
		try {
			CartDTO cartDTO=new CartDTO();
			cartDTO.setBuyerId(Integer.parseInt(splitMsg[0]));
			cartDTO.setProdId(Integer.parseInt(splitMsg[1]));
			cartDTO.setQuantity(Integer.parseInt(splitMsg[2]));
			
			boolean response=new RestTemplate().postForObject(userCartUri+"addProduct/",cartDTO,Boolean.class);
			
			if(response) {
				logger.info("Product added successfully");
			}else {
				logger.info("something went wrong while adding product");
			}
		}catch(Exception e) {
			logger.info("KafkaConsumer:"+e.getMessage());
		}
    }
}
