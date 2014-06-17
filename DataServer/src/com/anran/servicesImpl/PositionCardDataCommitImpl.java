package com.anran.servicesImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.pojo.PositionCard;

public class PositionCardDataCommitImpl {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	
	
	public int[] insertBatchPositionData(final List<PositionCard> pc) {

		int[] countExec = new int[] {};

		try {
			
			String sql = "insert into dbo.T_PERSON_LOCATE_IMM (PERSON_NUM , STATION_NUM , LOCATE_TIME , COMPASS_NUM , STATUS)values(? , ? , ? , ? , ?)";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, pc.get(i).getCardNum());
					ps.setString(2, pc.get(i).getIpAdress());
					ps.setString(3, pc.get(i).getTimeStamp());
					ps.setString(4, pc.get(i).getCompassNum());
					ps.setString(5, pc.get(i).getStatus());
				}

				public int getBatchSize() {
					// TODO Auto-generated method stub
					return pc.size();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return countExec;
	}

}
