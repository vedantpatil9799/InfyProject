package com.infy.OrderMS;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderDetailsDTO;
import com.infy.OrderMS.entity.ProductsOrder;
import com.infy.OrderMS.repository.OrderRepository;
import com.infy.OrderMS.repository.ProductOrderRepository;
import com.infy.OrderMS.service.OrderService;

@SpringBootTest
public class OrderMsApplicationTests {

}
