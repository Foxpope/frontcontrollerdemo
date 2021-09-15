package com.revature.service;

import java.util.Optional;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {

	private EmployeeDao edao;
	
	//introduce dependency injection
	public EmployeeService(EmployeeDao ed) {
		super();
		this.edao = ed;
	}
	
	//method
	public Employee confirmLogin(String username, String pwd) {
		Optional<Employee> emp = edao.findAll() //allows me to have a 
				.stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(pwd))) //filters to match uname and pwd
				.findFirst(); //assuming there is only one of these, it will only return that one instance
		
		return (emp.isPresent() ? emp.get() : null);
	}
	
	public int insert(Employee e) {
		return edao.insert(e);
	}
	
	public boolean update(Employee e) {
		return edao.update(e);
	}
	
	public boolean delete(Employee e) {
		return edao.delete(e);
	}
	
}
