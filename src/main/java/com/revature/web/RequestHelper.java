package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		out.print(json);
		log.info("returning request to see all users");
		
	}
	
	public static void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//we need to capture the user input and split up the username and password
		BufferedReader reader = request.getReader();
		
		StringBuilder s = new StringBuilder();
		
		//transfer everything over to the string builder from the buffered reader
		String line = reader.readLine();
		
		while (line != null) {
			s.append(line);
			line = reader.readLine(); //request looks like "username=bob&password=pass"
		}
		
		String body = s.toString();
		String[] sepByAmp = body.split("&");
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { //each element in thearray looks like "username=bob password=pass"
			values.add(pair.substring(pair.indexOf("=") + 1));
		}
		
		//capture the actual uname and pwd
		String username = values.get(0);
		String password= values.get(1);
		
		log.info("User attemted login with username: " + username);
		
		Employee e = eserv.confirmLogin(username, password);
		
		if (e != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", e);
			
			//print the logged in user t oscreen
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			//converty the object with mapper
			out.println(om.writeValueAsString(e));
			
			//log it
			log.info("the user " + username + " has logged in");
		} else {
			response.setStatus(204); //valid request, but no content to show
		}
		
		
	}
	
	public static void processError(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		//if something goes wrong, redirect the user to a custom 404 page
		request.getRequestDispatcher("error.html").forward(request, response);
		
	}

}
