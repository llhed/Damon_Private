package com.anran.servicesImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.services.PositionStation;




public class PositionStationImpl implements PositionStation{

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	
	/**
	 * 获得基站编号
	 * @param ipAddress
	 * @return
	 */
	public String[] getStationNum(String ipAddress){
		
		String sqlStr = "select BASE_DSE_NUM from dbo.T_BASE_STATION where BASE_NETLOOP_IPADDR = ?";
		String[] stationNum = new String[]{"",""};
		String result = "";
		String ip = this.getIpAdd(ipAddress);
		
		try{
			
			result = jdbcTemplate.queryForObject(sqlStr, new Object[]{ip}, String.class);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(!result.isEmpty()&&result != null){
			stationNum[0] = result.substring(0, 2);
			stationNum[1] = result.substring(2, 4);
		}
		
		return stationNum;
	}
	
	
	
	private String getIpAdd(String ipAdress){
		
		String ipStr[] = ipAdress.split(":");
		String ip = ipStr[0].substring(1);
		
		return ip;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
