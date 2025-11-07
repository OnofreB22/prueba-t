package com.onofreb22.pruebat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onofreb22.pruebat.entity.Client;
import com.onofreb22.pruebat.entity.Item;
import com.onofreb22.pruebat.entity.Order;
import com.onofreb22.pruebat.entity.OrderItem;
import com.onofreb22.pruebat.repository.ClientRepository;
import com.onofreb22.pruebat.repository.ItemRepository;
import com.onofreb22.pruebat.repository.OrderItemRepository;
import com.onofreb22.pruebat.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Transactional
    public Order createOrder(Integer clientId, List<Map<String, Object>> items) {
        // Buscar cliente
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        
        // Crear y guardar orden
        Order order = new Order(client);
        Order savedOrder = orderRepository.save(order);
        
        // Crear y guardar cada OrderItem, y agregarlo a la lista @Transient
        for (Map<String, Object> itemData : items) {
            Integer itemId = Integer.valueOf(itemData.get("itemId").toString());
            Integer quantity = Integer.valueOf(itemData.get("quantity").toString());
            
            // Validar cantidad
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            
            // Buscar item
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
            
            // Crear y guardar OrderItem en BD
            OrderItem orderItem = new OrderItem(savedOrder, item, quantity);
            orderItemRepository.save(orderItem);
        }
        
        // Retornar la orden con la lista de items poblada en memoria
        return order;
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItems(Integer orderId) {
        // Verificar que la orden existe
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        // Obtener todos los OrderItems de esa orden
        return orderItemRepository.findByOrder(order);
    }
}
