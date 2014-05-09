package com.anran.services;

import org.apache.mina.core.session.IoSession;

import com.anran.pojo.DeviceWorkImm;

public interface DeviceWorkImmServices {
	
	

	void insertDeviceWorkImm(DeviceWorkImm dwi);
	
	
	void deviceAna_Conn(IoSession session);
	
	
	
}
