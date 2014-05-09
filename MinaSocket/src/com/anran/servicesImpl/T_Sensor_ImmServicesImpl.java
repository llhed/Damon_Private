package com.anran.servicesImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import com.anran.pojo.T_SENSOR_IMM;
import com.anran.services.T_Sensor_ImmServices;

public class T_Sensor_ImmServicesImpl implements T_Sensor_ImmServices {

	
	private JdbcTemplate jdbcTemp;
		
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	
	/**
	 * 删除掉线的传感器信息
	 * @param sensor_Num
	 */
	public void deleteSenserImm(String sensor_Num){
		
		String sqlStr = "delete * from T_SENSOR_IMM where SENSOR_NUM = ?";
		
		try{
			jdbcTemp.update(sqlStr, sensor_Num);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 批量更新数据
	 * @param dataBeen 数据包容器
	 * @return
	 */
	public int[] updataBatchData(final List<T_SENSOR_IMM> dataBeen){
		
		int[] insertCount = new int[]{};
		insertCount = jdbcTemp.batchUpdate("update T_SENSOR_IMM set VALUE = ? , TIME = ? where SENSOR_NUM = ?",
			new BatchPreparedStatementSetter() {			
				public void setValues(PreparedStatement ps, int i) throws SQLException {					
					
					ps.setString(1, dataBeen.get(i).getValue());
		            ps.setString(2, dataBeen.get(i).getTime());
		            ps.setString(3, dataBeen.get(i).getSensor_Num());
		            
				}				
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return dataBeen.size();
				}
			}
		);
		
		return insertCount;
	}
	
	
	/**
	 * 批量插入数据
	 * @param dataBeen 数据包容器
	 * @return
	 */
	public int[] insertBatchData(final List<T_SENSOR_IMM> dataBeen ){
		
		int[] insertCount = new int[]{};
		insertCount = jdbcTemp.batchUpdate("insert into T_SENSOR_IMM (SENSOR_NUM , VALUE , TIME) values (?,?,?)",
			new BatchPreparedStatementSetter() {			
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					
					ps.setString(1, dataBeen.get(i).getSensor_Num());
					ps.setString(2, dataBeen.get(i).getValue());
		            ps.setString(3, dataBeen.get(i).getTime());
		            
				}				
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return dataBeen.size();
				}
			}
		);
		
		return insertCount;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static final class T_Sensor_ImmServicesImplMapper implements RowMapper{
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			T_SENSOR_IMM sensor_Imm = new T_SENSOR_IMM();
			
			sensor_Imm.setId(rs.getInt("ID"));
			sensor_Imm.setSensor_Num(rs.getString("SENSOR_NUM"));
			sensor_Imm.setTime(rs.getNString("TIME"));
			sensor_Imm.setValue(rs.getString("VALUE"));
			sensor_Imm.setAlert_Status(rs.getString("ALERT_STATUS"));
			
			return sensor_Imm;
		} 
		
	}
}
