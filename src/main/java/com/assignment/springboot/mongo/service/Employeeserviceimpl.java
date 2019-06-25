package com.assignment.springboot.mongo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.assignment.springboot.mongo.dao.Employeedao;
import com.assignment.springboot.mongo.model.Employee;
import com.assignment.springboot.mongo.model.SearchCriteria;
import com.assignment.springboot.mongo.model.SearchEntry;

@Service
public class Employeeserviceimpl implements Employeeservice {

	@Autowired
	private Employeedao dao;

	@Autowired
	private MongoOperations mongoOperatios;

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

	@Override
	public List<Employee> search(SearchCriteria searchCriteria) {
		Query query = new Query();
		Criteria aggregatedCriteria=null;
		for(int i=0; i<searchCriteria.getSearchEntries().size();i++) {
			SearchEntry entry = searchCriteria.getSearchEntries().get(i);
			if(entry.getOperator().equals("=")) {
				if(aggregatedCriteria==null) {
					aggregatedCriteria = Criteria.where(entry.getKey()).is(entry.getValue());
				}
				else {
					aggregatedCriteria.and(entry.getKey()).is(entry.getValue());
				}
			}
			
		}
		query.addCriteria(aggregatedCriteria);
		List<Employee>employees = mongoOperatios.find(query, Employee.class);
		return employees;
	}

}