package com.customermanagement.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customermanagement.management.dataprofile.SparkDataProfilingService;
import com.customermanagement.management.model.Customer;
import com.customermanagement.management.service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
    private CustomerService customerService;
	
	@Autowired
    private SparkDataProfilingService dataProfilingService;

	//Get all Customer Details
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    
    //Get Customer details by CustomerID
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Insert the customer details
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    	Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }
    
    //Update the customer details by CustomerID
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(customerId, updatedCustomer);
        return customer != null ?
                new ResponseEntity<>(customer, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
   //Delete the customer details by CustomerID
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return  ResponseEntity.ok("Deleted Successful");
    }
    
    //Insert the bulk customer details
    @PostMapping("/bulk-load")
    public ResponseEntity<String> bulkLoad(@RequestBody List<Customer> customers) {
    	customerService.bulkLoad(customers);
        return ResponseEntity.ok("Bulk load successful");
    }
    
    //Update the bulk customer details
    @PutMapping("/bulk-update")
    public ResponseEntity<String> bulkUpdateCustomers(@RequestBody List<Customer> updatedCustomers) {
    	customerService.bulkUpdate(updatedCustomers);
        return ResponseEntity.status(HttpStatus.OK).body("Bulk update successful");
    }
    
    //Get the profiling data
    @GetMapping("/data-profiling")
    public ResponseEntity<Map<String, Object>> getDataProfiling() {
        Map<String, Object> profilingResults = dataProfilingService.performDataProfiling();
        return ResponseEntity.ok(profilingResults);
    }
}
