package com.anran.services;

public interface Commands {

	
	
	/**
	 * 计算清包命令
	 * @param targetAdress 目标地址
	 * @param packageNum 包编号
	 * @return
	 */
	public byte[] cleanDataPackageCommand(String[] targetAdress , String[] packageNum);
	
	
	/**
	 * 获得请求数据的命令
	 * @param targetAdress
	 * @return
	 */
	public byte[] reqDataCommand(String[] targetAdress);
	
}
