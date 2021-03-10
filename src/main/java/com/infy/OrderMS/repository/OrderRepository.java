package com.infy.OrderMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.OrderMS.entity.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

}
