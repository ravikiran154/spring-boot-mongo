package com.assignment.springboot.mongo.controller;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.springboot.mongo.model.Employee;
import com.assignment.springboot.mongo.model.SearchCriteria;
import com.assignment.springboot.mongo.model.SearchEntry;
import com.assignment.springboot.mongo.service.Employeeservice;

@RestController
@RequestMapping(value= "/api/mongo/emp")
public class Employeecontroller {

	@Autowired
	Employeeservice serv;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Method to save employees in the db.
	 * @param emp
	 * @return
	 */
	@PostMapping(value= "/create")
	public String create(@RequestBody List<Employee> emp) {
		logger.debug("Saving employees.");
		serv.createEmployee(emp);
		return "Employee records created.";
	}

	/**
	 * Method to fetch all employees from the db.
	 * http://localhost:9090/api/mongo/emp/getall?page=0&size=2
	 * @return
	 */
	@GetMapping(value= "/getall")
	public Collection<Employee> getAll(Pageable pageable) {
		logger.debug("Getting all employees.");
		System.out.println("pageNumber :" + pageable.getPageNumber()+ " pagesize: "+ pageable.getPageSize());
		return serv.getAllEmployees(pageable);
	}

	/**
	 * Method to fetch employee by id.
	 * @param id
	 * @return
	 */
	@GetMapping(value= "/getbyid/{employee-id}")
	public Optional<Employee> getById(@PathVariable(value= "employee-id") int id) {
		logger.debug("Getting employee with employee-id= {}.", id);
		return serv.findEmployeeById(id);
	}

	/**
	 * Method to update employee by id.
	 * @param id
	 * @param e
	 * @return
	 */
	@PutMapping(value= "/update/{employee-id}")
	public String update(@PathVariable(value= "employee-id") int id, @RequestBody Employee e) {
		logger.debug("Updating employee with employee-id= {}.", id);
		e.setId(BigInteger.valueOf(id));
		serv.updateEmployee(e);
		return "Employee record for employee-id= " + id + " updated.";
	}

	/**
	 * Method to delete employee by id.
	 * @param id
	 * @return
	 */
	@DeleteMapping(value= "/delete/{employee-id}")
	public String delete(@PathVariable(value= "employee-id") int id) {
		logger.debug("Deleting employee with employee-id= {}.", id);
		serv.deleteEmployeeById(id);
		return "Employee record for employee-id= " + id + " deleted.";
	}

	/**
	 * Method to delete all employees from the db.
	 * @return
	 */
	@DeleteMapping(value= "/deleteall")
	public String deleteAll() {
		logger.debug("Deleting all employees.");
		serv.deleteAllEmployees();
		return "All employee records deleted.";
	}
	
	
	@GetMapping(value= "/search")
	public List<Employee> search() {
		//hard coded these values, but ideally they should come from UI
		SearchCriteria searchCriteria = new SearchCriteria();
		SearchEntry entry1 = new SearchEntry();
		SearchEntry entry2 = new SearchEntry();
		entry1.setKey("designation");
		entry1.setOperator("=");
		entry1.setValue("Developer");
		entry2.setKey("name");
		entry2.setOperator("=");
		entry2.setValue("ravi");
		searchCriteria.setSearchEntries(Arrays.asList(entry1,entry2));
		return serv.search(searchCriteria);
	}
}