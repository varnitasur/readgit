package com.example.mindtree.service.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindtree.entity.Employee;
import com.example.mindtree.repository.EmployeeRepository;
import com.example.mindtree.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public String writeDataIntoFileDB() {

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("Employee Details");

		Row rowHeader = sheet.createRow(0);
		rowHeader.createCell(0).setCellValue("Employee ID");
		rowHeader.createCell(1).setCellValue("Employee Name");
		rowHeader.createCell(2).setCellValue("Employee Department");
		rowHeader.createCell(3).setCellValue("Employee location");

		List<Employee> employees = employeeRepository.findAll();

		int rowNum = 1;

		for (Employee employee : employees) {

			Row rowEmployee = sheet.createRow(rowNum++);
			rowEmployee.createCell(0).setCellValue(employee.getEmployeeId());
			rowEmployee.createCell(1).setCellValue(employee.getEmployeeName());
			rowEmployee.createCell(2).setCellValue(employee.getEmployeeDept());
			rowEmployee.createCell(3).setCellValue(employee.getLocation());
		}

		try {
			FileOutputStream file = new FileOutputStream("D:\\filehandle\\employee.xlsx");
			try {
				workbook.write(file);
				file.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return "Written Successfully";
	}

	@Override
	public List<Employee> readDataFromFile() {

		List<Employee> employees = new ArrayList<Employee>();

		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream("D:\\filehandle\\employee.xlsx");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputFile);

			XSSFSheet sheet = workbook.getSheetAt(0);

			int count = 0;

			for (Row row : sheet) {
				if (count > 0) {
					Employee employee = new Employee();
					employee.setEmployeeId((int) row.getCell(0).getNumericCellValue());
					employee.setEmployeeName(row.getCell(1).getStringCellValue());
					employee.setEmployeeDept(row.getCell(2).getStringCellValue());
					employee.setLocation(row.getCell(3).getStringCellValue());
					employees.add(employee);
				}
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return employees;
	}

	@Override
	public String writeDataIntoTxtFileDB() {

		List<Employee> employ = new ArrayList<Employee>();

		Employee emp1 = new Employee(1, "Varnita","Lawer","Kolkata");
		Employee emp2 = new Employee(2, "Aditi","Docter","Bangalore");
		Employee emp3 = new Employee(3, "Dev","Engineer","Delhi");
		employ.add(emp1);
		employ.add(emp2);
		employ.add(emp3);

		try {
			FileWriter fileWriter = new FileWriter("D:\\filehandle\\output.txt");

			for (Employee employee : employ) {
				fileWriter.write(employee.toString());
			}

			fileWriter.close();
			FileOutputStream file1 = new FileOutputStream(new File("D:\\filehandle\\employees.txt"));
			ObjectOutputStream op = new ObjectOutputStream(file1);
			op.writeObject(employ);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialiation

		try {
			FileInputStream file2 = new FileInputStream("D:\\filehandle\\employees.txt");
			ObjectInputStream ip = new ObjectInputStream(file2);

			try {
				List<Employee> employees = (List<Employee>) ip.readObject();
				System.out.println(employees.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Written into text file";
	}

}
