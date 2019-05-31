package com.assignment.springboot.mongo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.assignment.springboot.mongo.model.Employee;

@Repository
public interface Employeedao extends PagingAndSortingRepository<Employee, Integer> {

	Page<Employee> findAll(Pageable pageable);
}