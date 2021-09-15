package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class RequestHelper {

	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	private static ObjectMapper om = new ObjectMapper();

	public static void processEmployees(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		response.setContentType("text/html");
		
		//2. get list of all employees
		List<Employee> allEmps = eserv.findAll();
		
		//3. turn th list of objects into a JSON string
		String json = om.writeValueAsString(allEmps);
		
		//4. use a print writer to write the objects to the response body
		PrintWriter out = response.getWriter();
		out.print("did a thing here");
		
	}

}
