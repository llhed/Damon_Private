package com.anran.mpp.servicesImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.mpp.services.TestService;

public class TestServicesImpl implements TestService{
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int test(){
		String sql = "select count(0) from Result";
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	
}
