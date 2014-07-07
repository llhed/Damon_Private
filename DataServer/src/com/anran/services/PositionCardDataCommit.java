package com.anran.services;

import java.util.List;

import com.anran.pojo.PositionCard;

public interface PositionCardDataCommit {

	/**
	 * 批量插入人员卡数据
	 * @param pc
	 * @return
	 */
	int[] insertBatchPositionData(final List<PositionCard> pc);
	
	
	/**
	 * 判断是否是有发送命令权限的IP
	 * @param ipAddress
	 * @return
	 */
	boolean isSendCommandIP(String ipAddress);
	
}
