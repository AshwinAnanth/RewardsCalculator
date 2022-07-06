package com.rewards.rewardsCalculator.repository;

import com.rewards.rewardsCalculator.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByCustomerId(Long customerId);
}
