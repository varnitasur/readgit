package com.example.mindtree.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mindtree.entity.Employee;

@Service
public interface EmployeeService {

	String writeDataIntoFileDB();

	List<Employee> readDataFromFile();

	String writeDataIntoTxtFileDB();

}
