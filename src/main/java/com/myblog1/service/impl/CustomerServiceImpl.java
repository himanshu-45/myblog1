package com.myblog1.service.impl;

import com.myblog1.entities.Customer;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.CustomerDTO;
import com.myblog1.repository.CustomerRepository;
import com.myblog1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return modelMapper.map(customer, CustomerDTO.class);
    }
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        // map CustomerDTO to Customer entity
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        // save customer entity
        Customer savedCustomer = customerRepository.save(customer);
        // map saved customer entity back to CustomerDTO
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public void deleteCustomerById(long id) {
        customerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with id: " + id)
        );
        customerRepository.deleteById(id);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + id)
        );
        // Update the existing customer entity with the new values from the DTO
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());

        // Save the updated customer entity to the database
        Customer save = customerRepository.save(customer);
        return modelMapper.map(save, CustomerDTO.class);
    }

}
