package com.anran.servicesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anran.pojo.Person;
import com.anran.services.PersonServices;

public class PersonServicesImpl implements PersonServices{
	
	
	
	private JdbcTemplate jdbcTemp;
		
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}


	/**
	 * 
	 * @param personNumList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String,Person> getPersons(List<String> personNumList){
		
		HashMap<String,Person> personMap = new HashMap<String,Person>();
		List<Person> personList = new ArrayList<Person>();
		
		StringBuilder sb = new StringBuilder();
		
		for(String str : personNumList){
			sb.append("'" + str + "'" + ",");
		}
		sb.deleteCharAt(sb.length()-1);
		
		String sql = "select PERSON_NUM , PERSON_NAME from T_PERSON where PERSON_NUM in(" +sb.toString()+")";
		
		try{
			personList = jdbcTemp.query(sql, new PersonMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(Person person : personList){
			personMap.put(person.getPERSON_NUM(), person);
		}
		return personMap;
	} 
	
	/**
	 * 获得基站的人数限制
	 * @param ipAdress
	 * @return
	 */
	public String getStationPersonLimit(String ipAdress){
		String result = null;
		String sqlStr = "select LIMIT_PERSON_NUM from IpAdress_NodeID Where BASE_NETLOOP_IPADDR = ?";
		
		try{
			
			result = jdbcTemp.queryForObject(sqlStr, new Object[]{ipAdress}, String.class);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	
	public static final class PersonMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Person person = new Person();
			
			person.setPERSON_NAME(rs.getString("PERSON_NAME"));
			person.setPERSON_NUM(rs.getString("PERSON_NUM"));
			
			return person;
		}
		
	}
}
