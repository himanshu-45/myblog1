package com.myblog1.controller;

import com.myblog1.payload.CustomerDTO;
import com.myblog1.payload.OrderDTO;
import com.myblog1.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //http://localhost:8080/api/orders/{id}
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    //http://localhost:8080/api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    //http://localhost:8080/api/orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@RequestParam("customer") @Valid CustomerDTO customerDTO) {
        List<OrderDTO> orders = orderService.getOrdersByCustomer(customerDTO);
        return ResponseEntity.ok(orders);
    }

    //http://localhost:8080/api/orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>("Order is deleted", HttpStatus.OK);
    }

    //http://localhost:8080/api/orders/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderDTO, id);
        return ResponseEntity.ok(updatedOrderDTO);
    }
}