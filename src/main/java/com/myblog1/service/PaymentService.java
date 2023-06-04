package com.myblog1.service;

import com.myblog1.payload.OrderDTO;
import com.myblog1.payload.PaymentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    public PaymentDTO createPayment(PaymentDTO paymentDTO);
    public Page<PaymentDTO> getAllPayments(Pageable pageable);

    public PaymentDTO getPaymentById(long id);
    void deletePayment(long id);
    public List<PaymentDTO> getPaymentsByOrder(OrderDTO orderDTO);
}