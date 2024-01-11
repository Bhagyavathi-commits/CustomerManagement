package com.customermanagement.management.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name="customersdetails")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Column(name="name")
    private String name;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(name="email")
    private String email;
    
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(name="phone")
    private String phone;
    
    @NotBlank(message = "Address is required")
    @Column(name="address")
    private String address;
    
    @NotBlank(message = "Company name is required")
    @Column(name="companyName")
    private String companyName;
    
    @NotBlank(message = "Industry type is required")
    @Column(name="industryType")
    private String industryType;
    
    @NotBlank(message = "Customer status is required")
    @Column(name="customerStatus")
    private String customerStatus;
    
    @NotBlank(message = "Account manager is required")
    @Column(name="accountManager")
    private String accountManager;

    // Audit columns
    
    @Column(name="createdDate")
    private LocalDateTime createdDate;
    @Column(name="lastModifiedDate")
    private LocalDateTime lastModifiedDate;
    
    //getter and setter methods
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
    
	//update data
	public void updateFrom(Customer updatedCustomer) {
			if (updatedCustomer.getName() != null) {
	            this.setName(updatedCustomer.getName());
	        }
	        if (updatedCustomer.getEmail() != null) {
	            this.setEmail(updatedCustomer.getEmail());
	        }
	        if (updatedCustomer.getPhone() != null) {
	            this.setPhone(updatedCustomer.getPhone());
	        }
	        if (updatedCustomer.getAddress() != null) {
	            this.setAddress(updatedCustomer.getAddress());
	        }
	        if (updatedCustomer.getCompanyName() != null) {
	            this.setCompanyName(updatedCustomer.getCompanyName());
	        }
	        if (updatedCustomer.getIndustryType() != null) {
	            this.setIndustryType(updatedCustomer.getIndustryType());
	        }
	        if (updatedCustomer.getCustomerStatus() != null) {
	            this.setCustomerStatus(updatedCustomer.getCustomerStatus());
	        }
	        if (updatedCustomer.getAccountManager() != null) {
	            this.setAccountManager(updatedCustomer.getAccountManager());
	        }
	       if (updatedCustomer.getCreatedDate() != null) {
	            this.setCreatedDate(LocalDateTime.now());
	        }
	        if (updatedCustomer.getLastModifiedDate() != null) {
	            this.setLastModifiedDate(LocalDateTime.now());
	        }
	      }
	}    