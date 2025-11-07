package com.onofreb22.pruebat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onofreb22.pruebat.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{}
