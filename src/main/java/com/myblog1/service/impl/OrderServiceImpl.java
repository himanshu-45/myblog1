package com.myblog1.service.impl;

import com.myblog1.entities.Customer;
import com.myblog1.entities.Order;
import com.myblog1.entities.Product;
import com.myblog1.payload.CustomerDTO;
import com.myblog1.payload.OrderDTO;
import com.myblog1.payload.ProductDTO;
import com.myblog1.repository.OrderRepository;
import com.myblog1.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Order getOrder(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setDateTime(LocalDateTime.now()); // set current datetime
        order = orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }


    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id " + orderId));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        List<Order> orders = orderRepository.findByCustomer(customer);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id " + orderId));
        orderRepository.delete(order);
    }

    // this is the correct update method
    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));

        // update customer
        CustomerDTO customerDTO = orderDTO.getCustomer();
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        order.setCustomer(customer);

        // update products
        List<ProductDTO> productDTOs = orderDTO.getProducts();
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            products.add(product);
        }
        order.setProducts(products);

        // update total
        order.setTotal(orderDTO.getTotal());

        // update dateTime
        order.setDateTime(orderDTO.getDateTime());

        Order savedOrder = orderRepository.save(order);
        OrderDTO savedOrderDTO = new OrderDTO();
        savedOrderDTO.setId(savedOrder.getId());
        savedOrderDTO.setCustomer(customerDTO);
        savedOrderDTO.setProducts(productDTOs);
        savedOrderDTO.setTotal(savedOrder.getTotal());
        savedOrderDTO.setDateTime(savedOrder.getDateTime());
        return savedOrderDTO;
    }


//    @Override
//    public OrderDTO updateOrder(OrderDTO orderDTO, long id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
//
//        // update customer
//        CustomerDTO customerDTO = orderDTO.getCustomer();
//        Customer customer = modelMapper.map(customerDTO, Customer.class);
//        order.setCustomer(customer);
//
//        // update products
//        List<ProductDTO> productDTOs = orderDTO.getProducts();
//        List<Product> products = productDTOs.stream()
//                .map(productDTO -> modelMapper.map(productDTO, Product.class))
//                .collect(Collectors.toList());
//        order.setProducts(products);
//
//        // update total
//        order.setTotal(orderDTO.getTotal());
//
//        // update dateTime
//        order.setDateTime(orderDTO.getDateTime());
//
//        Order save = orderRepository.save(order);
//        return modelMapper.map(save, OrderDTO.class);
//    }



}
