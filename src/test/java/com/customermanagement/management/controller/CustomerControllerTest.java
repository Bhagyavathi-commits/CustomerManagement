package com.customermanagement.management.controller;

import com.customermanagement.management.dataprofile.SparkDataProfilingService;
import com.customermanagement.management.model.Customer;
import com.customermanagement.management.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private SparkDataProfilingService dataProfilingService;

    @InjectMocks
    private CustomerController customerController;
    
    private Customer testCustomer;

    @Test
    public void testGetAllCustomers() {
        List<Customer> mockCustomers = Arrays.asList(new Customer(), new Customer());
        when(customerService.getAllCustomers()).thenReturn(mockCustomers);
        List<Customer> result = customerController.getAllCustomers();
        assertEquals(2, result.size());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(mockCustomer));
        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCustomer, responseEntity.getBody());
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @BeforeEach
    public void setUp() {
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
    public void testCreateCustomer() {
        when(customerService.createCustomer(any())).thenReturn(testCustomer);
        ResponseEntity<?> responseEntity = customerController.createCustomer(testCustomer);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testCustomer, responseEntity.getBody());
        verify(customerService, times(1)).createCustomer(any());
    }

    @Test
    public void testUpdateCustomer() {
        when(customerService.updateCustomer(eq(1L), any())).thenReturn(testCustomer);
        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(1L, testCustomer);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testCustomer, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(eq(1L), any());
    }

    @Test
    public void testUpdateCustomerNotFound() {
        when(customerService.updateCustomer(eq(1L), any())).thenReturn(null);
        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(1L, testCustomer);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(eq(1L), any());
    }

    @Test
    public void testDeleteCustomer() {
        ResponseEntity<String> responseEntity = customerController.deleteCustomer(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deleted Successful", responseEntity.getBody());
        verify(customerService, times(1)).deleteCustomer(eq(1L));
    }

    @Test
    public void testBulkLoad() {
        List<Customer> customerList = Collections.singletonList(testCustomer);
        ResponseEntity<String> responseEntity = customerController.bulkLoad(customerList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bulk load successful", responseEntity.getBody());
        verify(customerService, times(1)).bulkLoad(eq(customerList));
    }

    @Test
    public void testBulkUpdateCustomers() {
        List<Customer> customerList = Collections.singletonList(testCustomer);
        ResponseEntity<String> responseEntity = customerController.bulkUpdateCustomers(customerList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bulk update successful", responseEntity.getBody());
        verify(customerService, times(1)).bulkUpdate(eq(customerList));
    }

    @Test
    public void testGetDataProfiling() {
        Map<String, Object> mockProfilingResults = mock(Map.class);
        when(dataProfilingService.performDataProfiling()).thenReturn(mockProfilingResults);
        ResponseEntity<Map<String, Object>> responseEntity = customerController.getDataProfiling();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockProfilingResults, responseEntity.getBody());
        verify(dataProfilingService, times(1)).performDataProfiling();
    }
}