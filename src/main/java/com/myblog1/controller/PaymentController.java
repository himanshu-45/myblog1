package com.myblog1.controller;

import com.myblog1.payload.OrderDTO;
import com.myblog1.payload.PaymentDTO;
import com.myblog1.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //  http://localhost:8080/api/payments
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    //  http://localhost:8080/api/payments
    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(Pageable pageable) {
        Page<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }
    //  http://localhost:8080/api/payments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable long id){
        PaymentDTO payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    //  http://localhost:8080/api/payments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentById(@PathVariable long id){
        paymentService.deletePayment(id);
        return new ResponseEntity<>("Payment is deleted", HttpStatus.OK);
    }
    //  http://localhost:8080/api/payments/order
    @GetMapping("/order")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByOrder(@RequestBody OrderDTO orderDTO) {
        List<PaymentDTO> paymentDTOList = paymentService.getPaymentsByOrder(orderDTO);
        return ResponseEntity.ok(paymentDTOList);
    }
}

