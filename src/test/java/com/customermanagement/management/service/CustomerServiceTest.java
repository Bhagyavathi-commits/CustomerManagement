package com.customermanagement.management.service;

import com.customermanagement.management.model.Customer;
import com.customermanagement.management.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testCustomer = new Customer();
        testCustomer.setCustomerId(1L);
        testCustomer.setName("Customer1");
        testCustomer.setEmail("customer1@example.com");
        testCustomer.setPhone("923456789");
        testCustomer.setAddress("123 Main St");
        testCustomer.setCompanyName("Test Company");
        testCustomer.setIndustryType("IT");
        testCustomer.setCustomerStatus("Active");
        testCustomer.setAccountManager("John Doe");
        testCustomer.setCreatedDate(LocalDateTime.now());
        testCustomer.setLastModifiedDate(LocalDateTime.now());  
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> mockCustomers = Arrays.asList(testCustomer, new Customer());
        when(customerRepository.findAll()).thenReturn(mockCustomers);
        List<Customer> result = customerService.getAllCustomers();
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        Optional<Customer> result = customerService.getCustomerById(1L);
        assertEquals(testCustomer, result.orElse(null));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        Customer createdCustomer = customerService.createCustomer(testCustomer);
        assertEquals(testCustomer, createdCustomer);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        Customer updatedCustomer = customerService.updateCustomer(1L, testCustomer);
        assertEquals(testCustomer, updatedCustomer);
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
   public void testUpdateCustomerNotFound() {
        when(customerRepository.existsById(1L)).thenReturn(false);
        Customer updatedCustomer = customerService.updateCustomer(1L, testCustomer);
        assertEquals(null, updatedCustomer);
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBulkLoad() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        List<Customer> customerRequests = Arrays.asList(
                createTestCustomer("Customer1", "customer1@example.com", "923456789", "123 Main St"),
                createTestCustomer("Customer2", "customer2@example.com", "987654321", "456 Oak St"));

        List<Customer> convertedCustomers = Arrays.asList(
                createTestCustomer("ConvertedCustomer1", "customer1@example.com", "923456789", "123 Main St"),
                createTestCustomer("ConvertedCustomer2", "customer2@example.com", "987654321", "456 Oak St"));

        when(customerRepository.saveAll(any())).thenReturn(convertedCustomers);
        List<Customer> result = customerService.bulkLoad(customerRequests);
        assertEquals(convertedCustomers.size(), result.size());
        verify(customerRepository, times(1)).saveAll(any());
    }
    
    @Test
    public void testBulkUpdate() {
        List<Customer> updatedCustomers = Arrays.asList(createTestCustomer("updateCustomer1", "updatecustomer1@example.com", "923456679", "183 Main St"), createTestCustomer("updateCustomer2", "updatecustomer2@example.com", "987657321", "956 Oak St"));
        List<Customer> existingCustomers = Arrays.asList(createTestCustomer("Customer1", "customer1@example.com", "923456789", "123 Main St"), createTestCustomer("Customer2", "customer2@example.com", "987654321", "456 Oak St"));

        when(customerRepository.findAllById(any())).thenReturn(existingCustomers);
        when(customerRepository.saveAll(any())).thenReturn(updatedCustomers);

        customerService.bulkUpdate(updatedCustomers);

        assertEquals(updatedCustomers.size(), existingCustomers.size());
        verify(customerRepository, times(1)).findAllById(any());
        verify(customerRepository, times(1)).saveAll(existingCustomers);
    }
    private Customer createTestCustomer(String name, String email, String phone, String address) {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCompanyName("Test Company");
        customer.setIndustryType("IT");
        customer.setCustomerStatus("Active");
        customer.setAccountManager("John Doe");
        customer.setCreatedDate(LocalDateTime.now());
        customer.setLastModifiedDate(LocalDateTime.now());
        return customer;
    }
}

