package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static Session s;
	
	private static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	public static Session getSession() {
		
		if (s == null) {
			s = sf.openSession();
		}
		
		return s;
		
	}
	
	public static void closeSes() {
		s.close();
		sf.close();
	}
	
}
