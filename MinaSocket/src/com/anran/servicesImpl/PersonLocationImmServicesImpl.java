package com.anran.servicesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.mina.core.session.IoSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anran.pojo.PersonLocationImm;
import com.anran.services.PersonLocationImmServices;

public class PersonLocationImmServicesImpl implements PersonLocationImmServices {

	
	private JdbcTemplate jdbcTemp;

	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	
	public void insertPersonLocation(PersonLocationImm pli){
		
		String strSql = "insert into T_PERSON_LOCATE_IMM (PERSON_NUM , STATION_NUM)values(? , ?)";
		try{
			jdbcTemp.update(strSql, pli.getPersonNum() , pli.getNodeIdIndex());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 监听，解析并存储人员识别卡信息
	 * @param session
	 * @param message
	 */
	
	public void personLocationAna_Post(IoSession session, Object message){
		
		
		
	}
	
	
	public static final class PersonLocationImmMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			PersonLocationImm pli = new PersonLocationImm();
			
			pli.setPersonNum(rs.getString("PERSON_NUM"));
			pli.setNodeIdIndex(rs.getString("STATION_NUM"));
			
			return pli;
		}
		
		
	}
	
	
}
