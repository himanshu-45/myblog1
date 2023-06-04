package com.myblog1.service;

import com.myblog1.entities.Order;
import com.myblog1.payload.CustomerDTO;
import com.myblog1.payload.OrderDTO;

import java.util.List;

public interface OrderService {

    public Order getOrder(long id);
    public OrderDTO createOrder(OrderDTO orderDTO);
    public OrderDTO getOrderById(Long orderId);
    public List<OrderDTO> getOrdersByCustomer(CustomerDTO customerDTO);
    public void deleteOrderById(Long orderId);
    public OrderDTO updateOrder(OrderDTO orderDTO, long id);

}
