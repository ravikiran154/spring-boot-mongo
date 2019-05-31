package com.assignment.springboot.mongo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.assignment.springboot.mongo.model.Employee;

public interface Employeeservice {

	/**
	 * Method to create new employees in the db using mongo-db repository.
	 * @param emp
	 */
	public void createEmployee(List<Employee> emp);

	/**
	 * Method to fetch all employees from the db using mongo-db repository.
	 * @return
	 */
	public Collection<Employee> getAllEmployees(Pageable pageable);

	/**
	 * Method to fetch employee by id using mongo-db repository.
	 * @param id
	 * @return
	 */
	public Optional<Employee> findEmployeeById(int id);

	/**
	 * Method to delete employee by id using mongo-db repository.
	 * @param id
	 */
	public void deleteEmployeeById(int id);

	/**
	 * Method to update employee by id using mongo-db repository.
	 * @param id
	 */
	public void updateEmployee(Employee emp);

	/**
	 * Method to delete all employees using mongo-db repository.
	 */
	public void deleteAllEmployees();
}