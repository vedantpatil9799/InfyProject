package com.infy.OrderMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.OrderMS.entity.CompositeKey;
import com.infy.OrderMS.entity.ProductsOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductsOrder, CompositeKey>{

}
