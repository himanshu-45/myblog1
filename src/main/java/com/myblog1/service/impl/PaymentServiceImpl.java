package com.myblog1.service.impl;

import com.myblog1.entities.Order;
import com.myblog1.entities.Payment;
import com.myblog1.payload.OrderDTO;
import com.myblog1.payload.PaymentDTO;
import com.myblog1.repository.PaymentRepository;
import com.myblog1.service.OrderService;
import com.myblog1.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        // map PaymentDTO to Payment entity
        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        // get the Order object for the given order id
        Order order = orderService.getOrder(paymentDTO.getOrder().getId());

        // set the Order object in the Payment entity
        payment.setOrder(order);

        // save the Payment entity
        Payment savedPayment = paymentRepository.save(payment);

        // map Payment entity back to PaymentDTO and return
        return modelMapper.map(savedPayment, PaymentDTO.class);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> paymentPage = paymentRepository.findAll(pageable);

        List<PaymentDTO> paymentDTOList = paymentPage.getContent().stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(paymentDTOList, pageable, paymentPage.getTotalElements());
    }

    @Override
    public PaymentDTO getPaymentById(long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Payment not found with id " + id)
        );
        return modelMapper.map(payment, PaymentDTO.class);
    }

    @Override
    public void deletePayment(long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Payment not found with id " + id)
        );
        paymentRepository.delete(payment);
    }
    @Override
    public List<PaymentDTO> getPaymentsByOrder(OrderDTO orderDTO) {
        // map OrderDTO to Order entity
        Order order = modelMapper.map(orderDTO, Order.class);

        // find all payments for the given order
        List<Payment> payments = paymentRepository.findByOrder(order);

        // map List<Payment> to List<PaymentDTO> and return
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }


}

