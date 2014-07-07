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
	
	/**
	 * 获得所有基站数据
	 * @return
	 */
	public byte[] getAllDataCommand();
	
	/**
	 * 对人员定位卡发送报警命令
	 * @param commandWord
	 * @param pCardHigh
	 * @param pCardLow
	 * @return
	 */
	public byte[] personCardAlarmCommand(String commandWord , String pCardHigh , String pCardLow);
	
	/**
	 * 对给定基站下的识别卡发送报警命令
	 * @param stationHigh
	 * @param stationLow
	 * @param alarmCategray
	 * @return
	 */
	public byte[] alarmStationCommand(String stationHigh , String stationLow , String alarmCategray);
	
	
	/**
	 * 取消所有基站下人员卡的报警状态
	 * @return
	 */
	public byte[] cancelAllAlarm();
	
	/**
	 * 取消单张卡的报警
	 * @param cardNumHigh
	 * @param cardNumLow
	 * @return
	 */
	public byte[] cancelPersonNum(String cardNumHigh , String cardNumLow);
	
}
