package com.customermanagement.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customermanagement.management.model.Customer;
import com.customermanagement.management.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
	@Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer createCustomer(Customer customer) {
        customer.setCreatedDate(LocalDateTime.now());
        customer.setLastModifiedDate(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        if (customerRepository.existsById(customerId)) {
            updatedCustomer.setCustomerId(customerId);
            updatedCustomer.setCreatedDate(LocalDateTime.now());
            updatedCustomer.setLastModifiedDate(LocalDateTime.now());
            return customerRepository.save(updatedCustomer);
        }
        return null; 
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
    
    public List<Customer> bulkLoad(List<Customer> customerRequests) {
        List<Customer> customers = customerRequests.stream()
                .map(this::convertToCustomer)
                .collect(Collectors.toList());
        return customerRepository.saveAll(customers);
    }

    private Customer convertToCustomer(Customer requestDto) {
        Customer customer = new Customer();
        customer.setName(requestDto.getName());
        customer.setEmail(requestDto.getEmail());
        customer.setPhone(requestDto.getPhone());
        customer.setAddress(requestDto.getAddress());
        customer.setCompanyName(requestDto.getCompanyName());
        customer.setIndustryType(requestDto.getIndustryType());
        customer.setCustomerStatus(requestDto.getCustomerStatus());
        customer.setAccountManager(requestDto.getAccountManager());
        customer.setCreatedDate(LocalDateTime.now());
        customer.setLastModifiedDate(LocalDateTime.now());
		return customer;
    }
    
    public void bulkUpdate(List<Customer> updatedCustomers) {
        List<Customer> existingCustomers = customerRepository.findAllById(getCustomerIds(updatedCustomers));
        for (Customer existingCustomer : existingCustomers) {
            Customer updatedCustomer = getUpdatedCustomer(existingCustomer, updatedCustomers);
            existingCustomer.updateFrom(updatedCustomer);
        }
        customerRepository.saveAll(existingCustomers);
    }
    private List<Long> getCustomerIds(List<Customer> customers) {
        return customers.stream()
                .map(Customer::getCustomerId)
                .collect(Collectors.toList());
    }
   private Customer getUpdatedCustomer(Customer existingCustomer, List<Customer> updatedCustomers) {
        return updatedCustomers.stream()
                .filter(c -> c.getCustomerId().equals(existingCustomer.getCustomerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No corresponding updated customer found."));
    }
}
