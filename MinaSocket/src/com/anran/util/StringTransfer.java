package com.anran.util;

public class StringTransfer {

	
	/**
	 * 返回IP地址
	 * @param remoteAdress
	 * @return
	 */
	public String getIPAddress(String remoteAdress){
		
		return remoteAdress.split(":")[0].substring(1, remoteAdress.split(":")[0].length()-1);
	}
	
	/**
	 * 返回端口
	 * @param remoteAdress
	 * @return
	 */
	public String getPort(String remoteAdress){
		return remoteAdress.split(":")[0];
	}

}
