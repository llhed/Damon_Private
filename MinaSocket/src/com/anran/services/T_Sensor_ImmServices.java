package com.anran.services;

import java.util.List;

import com.anran.pojo.T_SENSOR_IMM;

public interface T_Sensor_ImmServices {
	
	/**
	 * ʵʱ���ݵ���������
	 * @param dataBeen
	 * @return
	 */
	int[] insertBatchData(final List<T_SENSOR_IMM> dataBeen );
	
	/**
	 * ʵʱ���ݵ���������
	 * @param dataBeen
	 * @return
	 */
	int[] updataBatchData(final List<T_SENSOR_IMM> dataBeen);
	
	/**
	 * ɾ�����ߵĴ�����
	 * @param sensor_Num
	 */
	void deleteSenserImm(String sensor_Num);
	

}
