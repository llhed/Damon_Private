package com.anran.services;

import org.apache.mina.core.session.IoSession;

import com.anran.pojo.PersonLocationImm;

public interface PersonLocationImmServices {

	/**
	 * ������Ա��Ϣ
	 * @param pli
	 */
	void insertPersonLocation(PersonLocationImm pli);
	
	/**
	 * ��Աʶ����Ϣ����
	 * @param session
	 * @param message
	 */
	void personLocationAna_Post(IoSession session, Object message);
}
