package com.anran.servicesImpl;





import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.services.HelloWorldBo;

public class HelloWorldBoImpl implements HelloWorldBo{

	private JdbcTemplate jdbcTemplate;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}




	@Override
	public String getHelloWorld() {
		// TODO Auto-generated method stub
		
		String sql = "select count(0) from Result";
		int count = jdbcTemplate.queryForInt(sql);
		
		return String.valueOf(count);
	}

}
