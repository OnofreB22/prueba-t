package com.onofreb22.pruebat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onofreb22.pruebat.entity.Order;
import com.onofreb22.pruebat.entity.OrderItem;
import com.onofreb22.pruebat.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request) {
        try {
            // Extraer datos del request
            Integer clientId = Integer.valueOf(request.get("clientId").toString());
            List<Map<String, Object>> items = extractItems(request);
            
            // Crear orden
            Order order = orderService.createOrder(clientId, items);
            
            // Obtener los OrderItems creados
            List<OrderItem> orderItems = orderService.getOrderItems(order.getId());
            
            // Crear response manual con Map
            Map<String, Object> response = new HashMap<>();
            response.put("order", order);
            response.put("items", orderItems);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/prueba/{id}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable Integer id) {
        try {
            // Obtener la orden
            Order order = orderService.getOrderById(id);
            
            // Obtener los OrderItems de esa orden
            List<OrderItem> orderItems = orderService.getOrderItems(id);
            
            // Crear response con Order y sus items
            Map<String, Object> response = new HashMap<>();
            response.put("order", order);
            response.put("items", orderItems);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> extractItems(Map<String, Object> request) {
        return (List<Map<String, Object>>) request.get("items");
    }
}
