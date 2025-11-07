package com.onofreb22.pruebat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onofreb22.pruebat.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{}
