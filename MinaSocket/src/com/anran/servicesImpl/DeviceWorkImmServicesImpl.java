package com.anran.servicesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.mina.core.session.IoSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anran.pojo.DeviceWorkImm;
import com.anran.services.DeviceWorkImmServices;
import com.anran.util.StringTransfer;

public class DeviceWorkImmServicesImpl implements DeviceWorkImmServices {
	
	private JdbcTemplate jdbcTemp;
	
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	public void insertDeviceWorkImm(DeviceWorkImm dwi){
		
		String strSql = "insert into T_DEVICE_WORK_IMM (DEVICE_NUM , DEVICE_STATUS)values(? , ?)";
		
		try{
			jdbcTemp.update(strSql, dwi.getDevice_Num() , dwi.getDevice_Status());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	/**
	 * 监听，解析并存储基站信息
	 * @param session
	 */

	public  void deviceAna_Conn(IoSession session){
		StringTransfer st = new StringTransfer();
		DeviceWorkImm dwi = new DeviceWorkImm();
		
		session.setAttribute("ip", st.getIPAddress(session.getRemoteAddress().toString()));

		if(session.getRemoteAddress().toString() != null){
			
			dwi.setDevice_Num(st.getIPAddress(session.getRemoteAddress().toString()));

			dwi.setDevice_Status("1");
			
			this.insertDeviceWorkImm(dwi);
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static final class DeviceWorkImmServicesMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			DeviceWorkImm dwi = new DeviceWorkImm();
			
			dwi.setID(rs.getInt("ID"));
			dwi.setDevice_Num(rs.getString("DEVICE_NUM"));
			dwi.setDevice_Status(rs.getString("DEVICE_STATUS"));
			
			return dwi;
			
		}
		
	}
}
