package com.company.customerdataservice.controller;

import com.company.customerdataservice.dto.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository repo;

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(@RequestParam(required = false) String state, @RequestParam(required = false) String level) {
        System.out.println("testing!");

        if (state != null) {
            return repo.findByState(state);
        }
        if (level == "Gold" || level == "Silver" || level == "Bronze") {
            return repo.findByLevel(level);
        }

        return repo.findAll();
    }

    @GetMapping(value = "/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Customer> getAllCustomers(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping(value = "/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        return repo.save(customer);
    }

    @PutMapping(value = "/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer) {

        if (customer.getId() == null) {
            customer.setId(id);
        }

        if (customer.getId() != id) {
            throw new DataIntegrityViolationException("Your id in your Pathvariable and the id in your request body do not match");
        }

        System.out.println(customer);
       repo.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        repo.deleteById(id);
    }
}
