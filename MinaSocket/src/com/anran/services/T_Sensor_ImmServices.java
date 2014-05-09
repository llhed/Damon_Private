package com.anran.services;

import java.util.List;

import com.anran.pojo.T_SENSOR_IMM;

public interface T_Sensor_ImmServices {
	
	/**
	 * 实时数据的批量插入
	 * @param dataBeen
	 * @return
	 */
	int[] insertBatchData(final List<T_SENSOR_IMM> dataBeen );
	
	/**
	 * 实时数据的批量更新
	 * @param dataBeen
	 * @return
	 */
	int[] updataBatchData(final List<T_SENSOR_IMM> dataBeen);
	
	/**
	 * 删除离线的传感器
	 * @param sensor_Num
	 */
	void deleteSenserImm(String sensor_Num);
	

}
