package com.myblog1.service;

import com.myblog1.payload.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    public Page<CustomerDTO> getAllCustomers(Pageable pageable);

    public CustomerDTO getCustomerById(Long id);

    public CustomerDTO saveCustomer(CustomerDTO customerDTO);

    public void deleteCustomerById(long id);

    public CustomerDTO updateCustomer(CustomerDTO customerDTO, long id);
}
