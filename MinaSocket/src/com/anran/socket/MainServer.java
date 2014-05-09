package com.anran.socket;

import java.io.IOException;

import org.apache.mina.core.service.IoConnector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainServer {
	
	public static void main( String[] args ) throws IOException 
	{ 
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	} 
}
