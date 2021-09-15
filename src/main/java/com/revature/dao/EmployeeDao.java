package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;



public class EmployeeDao {

	public int insert(Employee e) {
		
		Session s = HibernateUtil.getSession();
		
		Transaction tx = s.beginTransaction();
		
		int pk = (int) s.save(e);
		
		tx.commit();
		
		return pk;
		
	}
	
	public boolean update(Employee e) {
		return false;
	}
	
	public boolean delete(Employee e) {
		return false;
	}
	
	public List<Employee> findAll() {
		Session s = HibernateUtil.getSession();
		
		List<Employee> emps = s.createQuery("from employee", Employee.class).list();
		
		return emps;
	}
	
}
