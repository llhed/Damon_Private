package com.anran.servicesImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anran.pojo.Node;
import com.anran.services.NodeServices;

public class NodeServicesImpl implements NodeServices{
	
	
	private JdbcTemplate jdbcTemp;

	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}

	
	
	/**
	 * 获得人员所在基站的Node结点信息
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String,Node> getNodeInfo(List<String> stationList){
		
		List<Node> nodeList = new ArrayList<Node>();
		HashMap<String,Node> nodeMap = new HashMap<String,Node>();
		
		StringBuilder sb = new StringBuilder();
				
		for(String item : stationList){
			sb.append("'" + item + "'" + ",");
		}
		
		sb.deleteCharAt(sb.length()-1);
		
		
		String sqlStr = "select *  FROM T_NODE WHERE BASESTATION_NUM in("+ sb.toString() +")";
		
		try{
			nodeList = jdbcTemp.query(sqlStr,  new NodeMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(Node node : nodeList){
			nodeMap.put(node.getBASESTATION_NUM(), node);
		}
		return nodeMap;
	}


	public static final class NodeMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
			Node node = new Node();
			
			node.setBASESTATION_NUM(rs.getString("BASESTATION_NUM"));
			node.setFADEAREA_FLAG(rs.getString("FADEAREA_FLAG"));
			node.setKEYAREA_FLAG(rs.getString("KEYAREA_FLAG"));
			node.setNODE_NAME(rs.getString("NODE_NAME"));
			node.setRESTRICTEDAREA_FLAG(rs.getString("RESTRICTEDAREA_FLAG"));
			
			return node;
		}
		
		
	}

}
