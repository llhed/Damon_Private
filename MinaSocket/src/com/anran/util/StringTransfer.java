package com.anran.util;

public class StringTransfer {

	
	/**
	 * ����IP��ַ
	 * @param remoteAdress
	 * @return
	 */
	public String getIPAddress(String remoteAdress){
		
		return remoteAdress.split(":")[0].substring(1, remoteAdress.split(":")[0].length()-1);
	}
	
	/**
	 * ���ض˿�
	 * @param remoteAdress
	 * @return
	 */
	public String getPort(String remoteAdress){
		return remoteAdress.split(":")[0];
	}

}
