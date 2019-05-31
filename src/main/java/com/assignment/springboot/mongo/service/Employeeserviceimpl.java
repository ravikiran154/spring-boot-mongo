package com.assignment.springboot.mongo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assignment.springboot.mongo.dao.Employeedao;
import com.assignment.springboot.mongo.model.Employee;

@Service
public class Employeeserviceimpl implements Employeeservice {

	@Autowired
	Employeedao dao;

	@Override
	public void createEmployee(List<Employee> emp) {
		dao.saveAll(emp);
	}

	@Override
	public Collection<Employee> getAllEmployees(Pageable pageable) {
		return dao.findAll(pageable).getContent();
	}

	@Override
	public Optional<Employee> findEmployeeById(int id) {
		return dao.findById(id);
	}

	@Override
	public void deleteEmployeeById(int id) {
		dao.deleteById(id);
	}

	@Override
	public void updateEmployee(Employee emp) {
		dao.save(emp);
	}

	@Override
	public void deleteAllEmployees() {
		dao.deleteAll();
	}
}