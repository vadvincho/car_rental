package com.vadzimvincho.controllers;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.services.api.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    String getAll(Model model) throws DaoException {
        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/customer-create")
    public String createCustomerForm(@ModelAttribute("customer") Customer customer) {
        return "customer-create";
    }

    @PostMapping("/customer-create")
    public String createUser(@ModelAttribute("customer") Customer customer) throws DaoException {
        customerService.add(customer);
        return "redirect:/customers";
    }

    @GetMapping("customer-delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) throws DaoException {
        customerService.remove(customerService.getById(id));
        return "redirect:/customers";
    }

    @GetMapping("/customer-update/{id}")
    public String updateCustomerForm(@PathVariable("id") Long id, Model model) throws DaoException {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customer-update";
    }

    @PostMapping("/customer-update")
    public String updateCustomer(Customer customer) throws DaoException {
        customerService.update(customer);
        return "redirect:/customers";
    }
}
