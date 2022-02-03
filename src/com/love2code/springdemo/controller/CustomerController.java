package com.love2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.love2code.springdemo.dao.CustomerDAO;
import com.love2code.springdemo.entity.Customer;
import com.love2code.springdemo.service.CustomerService;
import com.love2code.springdemo.utils.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/*
	 * @GetMapping("/list") public String listCustomers(Model model) {
	 * 
	 * // get customers from dao List<Customer> customers =
	 * customerService.getCustomers();
	 * 
	 * // add the customer to model model.addAttribute("customers", customers);
	 * 
	 * return "list-customers"; }
	 */

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Customer customer = new Customer();

		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

		// save the customer
		customerService.saveCustomer(theCustomer);

		return "redirect:/customer/list";

	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model model) {

		Customer theCustomer = customerService.getCustomer(theId);

		model.addAttribute("customer", theCustomer);

		return "customer-form";

	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {

		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}

	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {
		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		return "list-customers";
	}

	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required = false) String sort) {

		// get customers from the service
		List<Customer> theCustomers = null;

		// check for sort field
		if (sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		} else {
			// no sort field provided ... default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}

		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";
	}

}
