package com.anran.socket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.util.Global;

public class MainDataServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		//初始化全局变量
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		
		String result = "";
		try{
			String sql = "select PARA_VALUE from T_SYSTEM_PARA where PARA_NAME = 'SEND_ALERT_IPADDR'";
			result = jdbcTemplate.queryForObject(sql ,  String.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String[] ips = result.split(",");
		
		Global.SENDER_COMMAND_IP = ips;
	}

}
