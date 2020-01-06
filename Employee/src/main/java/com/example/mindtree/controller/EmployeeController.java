package com.example.mindtree.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindtree.entity.Employee;
import com.example.mindtree.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@PostMapping("/writeintoexcelfile")
	public String writeDataIntoFile() {
	
		String response = service.writeDataIntoFileDB();
		
		return response;
	}
	
	@GetMapping("/readfromexcelfile")
	public List<Employee> readFromFile(){
		
		List<Employee> employees = service.readDataFromFile();
		return employees;
	}
	
	@PostMapping("/writeintotxtfile")
	public String writeDataIntoTxtFile() {
	
		String response = service.writeDataIntoTxtFileDB();
		
		return response;
	}

}
