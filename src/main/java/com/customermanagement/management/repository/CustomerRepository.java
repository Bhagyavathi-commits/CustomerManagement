package com.customermanagement.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customermanagement.management.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
