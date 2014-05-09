package com.anran.util;

public class PositionCardCommand {

	////对所有识别分站发送读卡命令
	public final static byte[] READ_POSITION_STATION= { (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xaa, (byte) 0x0f, (byte) 0x00,
		(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0x06,
		(byte) 0x13, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xd1, (byte) 0xe0 };
	
	
}
