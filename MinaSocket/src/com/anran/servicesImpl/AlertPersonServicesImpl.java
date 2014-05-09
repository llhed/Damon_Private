package com.anran.servicesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anran.pojo.AlertPerson;

public class AlertPersonServicesImpl {
	
	private JdbcTemplate jdbcTemp;

	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}


	
	
	public void addAlertPerson(AlertPerson ap){
		
		String sqlStr = "insert into T_ALERT_PERSON (PERSON_NUM , STATION_NUM)values(? , ?)";
		try{
			jdbcTemp.update(sqlStr, ap.getPersonNum() , ap.getStationNum());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static final class AlertPersonMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			AlertPerson ap = new AlertPerson();
			
			ap.setPersonNum(rs.getString("PERSON_NUM"));
			ap.setStationNum(rs.getString("STATION_NUM"));
			
			return ap;
		}
		
	}
}
