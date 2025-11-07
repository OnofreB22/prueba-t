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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    @Operation(
        summary = "Crear orden",
        description = "Crea una nueva orden con cliente e items"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Orden creada exitosamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Order.class),
            examples = @ExampleObject(
                name = "Respuesta exitosa",
                value = """
                    {
                        "items": [
                            {
                                "id": 18,
                                "item": {
                                    "id": 0,
                                    "name": "Plancha"
                                },
                                "quantity": 2
                            },
                            {
                                "id": 19,
                                "item": {
                                    "id": 1,
                                    "name": "Nevera"
                                },
                                "quantity": 5
                            }
                        ],
                        "order": {
                            "id": 15,
                            "client": {
                                "id": 0,
                                "fullName": "Onofre Benjumea"
                            },
                            "createdAt": "2025-11-07T14:27:08.763422629"
                        }
                    } 
                """
            )
        )
    )
    public ResponseEntity<Map<String, Object>> createOrder(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la orden a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de request",
                    value = """
                        {
                            "clientId": 1,
                            "items": [
                                {
                                    "itemId": 0,
                                    "quantity": 2
                                },
                                {
                                    "itemId": 1,
                                    "quantity": 5
                                }
                            ]
                        }
                    """
                )
            )
        )
        @Valid
        @RequestBody Map<String, Object> request) {
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

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener orden",
        description = "Recupera una orden por su ID con todos sus items"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Orden encontrada",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Order.class),
            examples = @ExampleObject(
                name = "Respuesta exitosa",
                value = """
                    {
                        "items": [
                            {
                                "id": 18,
                                "item": {
                                    "id": 0,
                                    "name": "Plancha"
                                },
                                "quantity": 2
                            },
                            {
                                "id": 19,
                                "item": {
                                    "id": 1,
                                    "name": "Nevera"
                                },
                                "quantity": 5
                            }
                        ],
                        "order": {
                            "id": 15,
                            "client": {
                                "id": 0,
                                "fullName": "Onofre Benjumea"
                            },
                            "createdAt": "2025-11-07T14:27:08.763422629"
                        }
                    } 
                """
            )
        )
    )
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
