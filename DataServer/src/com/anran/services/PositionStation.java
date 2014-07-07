package com.anran.services;

import com.anran.pojo.PostionStationPojo;

public interface PositionStation {

	
	/**
	 * 获得基站编号
	 * @param ipAddress
	 * @return
	 */
	public String[] getStationNum(String ipAddress);
	
	/**
	 * 插入基站信息
	 * @param psp
	 */
	public void insertPositionImm(PostionStationPojo psp);
	
}
