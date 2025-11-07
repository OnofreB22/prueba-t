package com.onofreb22.pruebat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onofreb22.pruebat.entity.Order;
import com.onofreb22.pruebat.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

    List<OrderItem> findByOrder(Order order);
}
