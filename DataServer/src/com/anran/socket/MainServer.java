package com.anran.socket;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MainServer implements ServletContextListener{
	
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.print("SYSTEM START");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	} 
	
	
}
