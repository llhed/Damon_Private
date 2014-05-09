package com.anran.services;

import org.apache.mina.core.session.IoSession;

import com.anran.pojo.PersonLocationImm;

public interface PersonLocationImmServices {

	/**
	 * 插入人员信息
	 * @param pli
	 */
	void insertPersonLocation(PersonLocationImm pli);
	
	/**
	 * 人员识别卡信息推送
	 * @param session
	 * @param message
	 */
	void personLocationAna_Post(IoSession session, Object message);
}
