package com.example.orderservice.controller;

import com.example.orderservice.dto.Order;
import com.example.orderservice.dto.OrderEvent;
import com.example.orderservice.publisher.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class OrderController {
    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("orders")
    public String placeOrder(@RequestBody Order order){
        order.setId(UUID.randomUUID().toString());

        OrderEvent event = new OrderEvent("PENDING", "Order is pending status", order);
        orderProducer.sendMessage(event);

        return "Order message sent to RabbitMQ ..";
    }
}
