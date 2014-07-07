package com.anran.socket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.anran.pojo.PositionCard;
import com.anran.pojo.PostionStationPojo;
import com.anran.services.Commands;
import com.anran.services.PositionCardDataCommit;
import com.anran.services.PositionStation;

public class DataHandler extends IoHandlerAdapter {

	private Logger logger = Logger.getLogger(MinaTimeServer.class);

	private Commands commands;
	private String[] targetAdress;
	private PositionStation positionStation;
	private NioSocketAcceptor acceptor;
	private PositionCardDataCommit positionCardDataCommit;
	
	
	
	public void setPositionCardDataCommit(PositionCardDataCommit positionCardDataCommit) {
		this.positionCardDataCommit = positionCardDataCommit;
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public void setPositionStation(PositionStation positionStation) {
		this.positionStation = positionStation;
	}

	public String[] getTargetAdress() {
		return targetAdress;
	}

	
	public void setTargetAdress(String[] targetAdress) {
		this.targetAdress = targetAdress;
	}

	public Commands getCommands() {
		return commands;
	}

	public void setCommands(Commands commands) {
		this.commands = commands;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		session.setAttribute("ip", session.getRemoteAddress().toString());
		PostionStationPojo psp = new PostionStationPojo();
		psp.setIpAddress(this.getIpAdd(session.getRemoteAddress().toString()));
		psp.setStatus("在线");
		positionStation.insertPositionImm(psp);
		session.write(IoBuffer.wrap(commands.getAllDataCommand()));

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
		PostionStationPojo psp = new PostionStationPojo();
		
		psp.setIpAddress(this.getIpAdd(session.getAttribute("ip").toString()));
		psp.setStatus("离线");
		positionStation.insertPositionImm(psp);
		
//		System.out.println("Close:" + session.getAttribute("ip") );
//		System.out.println("**************************" );
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		byte[] getDataCommand = commands.getAllDataCommand();
		session.write(IoBuffer.wrap(getDataCommand));
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		
		if(positionCardDataCommit.isSendCommandIP(session.getRemoteAddress().toString())){
			 
			List<String> dataStrList = ananlayCommandBuf(message);
			sendAlarmCommand(dataStrList, acceptor);
			
		}else{
			List<PositionCard> pCardList = new ArrayList();
			String[] packageNum = null;
			String[] targetAddressNum = null;
			List<String> dataStrList = new ArrayList();

			if (message != null) {
				dataStrList = ananlaysBuf(message);
				if (dataStrList.size() > 28) {

					pCardList = wrapCardData(dataStrList, session);
					//保存识别卡数据
					positionCardDataCommit.insertBatchPositionData(pCardList);
							
					packageNum = this.getPackageNum(dataStrList);
					targetAddressNum = this.getTargetAddress(dataStrList);
					
					for(PositionCard pc : pCardList){
						System.out.print(pc.getCardNum() + "," + pc.getCardStatus() + "," + pc.getTimeStamp() + "||" + pc.getCompassNum() + "||");
					}

					byte[] cleanPackage = commands.cleanDataPackageCommand(targetAddressNum, packageNum);

					session.write(IoBuffer.wrap(cleanPackage));
				}
			}
		}

	}

	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	/**
	 * 从缓存中读取字节码并进行转码
	 * 
	 * @param message
	 * @return
	 */
	private List<String> ananlaysBuf(Object message) {

		List<String> dataStrList = new ArrayList();
		IoBuffer buf = (IoBuffer) message;

		ByteBuffer bf = buf.buf();
		byte[] tmpBuffer = new byte[bf.limit()];
		bf.get(tmpBuffer);

		int bufLength = tmpBuffer.length;

		if (bufLength > 28) {
			for (byte b : tmpBuffer) {
				dataStrList.add(String.valueOf(b));
			}

		}

		return dataStrList;
	}
	
	/**
	 * 获得发送的命令的字节码
	 * @param message
	 * @return
	 */
	private List<String> ananlayCommandBuf(Object message) {

		List<String> dataStrList = new ArrayList();
		IoBuffer buf = (IoBuffer) message;

		ByteBuffer bf = buf.buf();
		byte[] tmpBuffer = new byte[bf.limit()];
		bf.get(tmpBuffer);

		int bufLength = tmpBuffer.length;
		
		for (byte b : tmpBuffer) {
				dataStrList.add(String.valueOf(b));
		}
		
		return dataStrList;
	}
	
	/**
	 * 转发客户端的报警命令
	 * @param dataStrList 字节码
	 * @param acceptor Socket适配器
	 */
	private void sendAlarmCommand(List<String> dataStrList , NioSocketAcceptor acceptor){
		
		int dataStrListLength = dataStrList.size();
		String commandWord = getByteHex(Integer.valueOf(dataStrList.get(1))) ;
		
		if(dataStrList.get(0).equals("1")){
			for(int i = 0 ; i < (dataStrListLength-2)/2 ; i++){
				
				String cardNumHigh = getByteHex(Integer.valueOf(dataStrList.get(2 + i*2)));
				String cardNumLow = getByteHex(Integer.valueOf(dataStrList.get(3 + i*2)));
				
				byte[] alarmCommand = commands.personCardAlarmCommand(commandWord, cardNumHigh, cardNumLow);
				
				sendConMessage(alarmCommand , acceptor);
				
			}
		}
		if(dataStrList.get(0).equals("2")){
			
			for(int i = 0 ; i < (dataStrListLength-2)/2 ; i++){
				String stationHigh = getByteHex(Integer.valueOf(dataStrList.get(2 + i*2)));
				String stationLow = getByteHex(Integer.valueOf(dataStrList.get(3 + i*2)));
				
				byte[] alarmstationCommand = commands.alarmStationCommand( stationHigh, stationLow , commandWord);
				
				sendConMessage(alarmstationCommand , acceptor);
				
			}
		}	
		
		if(dataStrList.get(0).equals("3")){
			
			for(int i = 0 ; i < (dataStrListLength-2)/2 ; i++){
				String cancelCardHigh = getByteHex(Integer.valueOf(dataStrList.get(2 + i*2)));
				String cancelCardLow = getByteHex(Integer.valueOf(dataStrList.get(3 + i*2)));
				
				byte[] cancelCardNumCommand = commands.cancelPersonNum(cancelCardHigh, cancelCardLow);
				sendConMessage(cancelCardNumCommand, acceptor);
				
			}	
			
			
		}
		
		if(dataStrList.get(0).equals("4")){
			
			byte[] cancelCommand = commands.cancelAllAlarm();
			sendConMessage(cancelCommand, acceptor);
		}
	}
	

	/**
	 * 获得单字节的16进制字符串
	 * 
	 * @param intByte
	 * @return
	 */
	private String getByteHex(int intByte) {

		String strHex = Integer.toHexString(intByte);
		if (intByte >= 0) {
			return strHex.length() == 1 ? "0" + strHex : strHex;
		} else {
			return strHex.substring(strHex.length() - 2, strHex.length());
		}

	}

	/**
	 * 对识别卡数据进行对象化封装
	 * 
	 * @param strList
	 * @return
	 */
	private List<PositionCard> wrapCardData(List<String> strList, IoSession session) {

		List<PositionCard> cardList = new ArrayList();
		PositionCard pCar = new PositionCard();
		int listLength = strList.size();
		StringBuilder sbTime = new StringBuilder();

		String ipAdress = session.getRemoteAddress().toString().split(":")[0].substring(1);
		String timeStamp = wrapDataTime(sbTime, strList, listLength);

		cardList = getObjectWrap(ipAdress, timeStamp, strList, listLength);

		System.out.println(cardList.size());
		return cardList;
	}

	/**
	 * 封装时间字符串 如："2014-06-23 08:00:00"
	 * 
	 * @param sbTime
	 * @param strList
	 * @param listLength
	 * @return
	 */
	private String wrapDataTime(StringBuilder sbTime, List<String> strList, int listLength) {

		sbTime.append("20");
		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 8))) );
		sbTime.append("-");
		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 7))));
		sbTime.append("-");
		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 6))));
		sbTime.append(" ");

		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 5))));
		sbTime.append(":");
		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 4))));
		sbTime.append(":");
		sbTime.append(getByteHex(Integer.valueOf(strList.get(listLength - 3))));

		return sbTime.toString();
	}

	/**
	 * 获取数据包的DATA部分，进行对象封装
	 * 
	 * @param ipAdress
	 *            IP地址
	 * @param timeStamp
	 *            时间戳
	 * @param strList
	 *            数据包String泛型数组
	 * @param listLength
	 *            数据包长度
	 * @return
	 */
	private List<PositionCard> getObjectWrap(String ipAdress, String timeStamp, List<String> strList, int listLength) {

		List<PositionCard> pCardList = new ArrayList();

		List<String> dataPartList = new ArrayList();

		// 确保数据包至少有一条记录
		if (listLength > 28 && strList.get(0).equals("1") && strList.get(1).equals("-1") && strList.get(2).equals("0")) {

			for (int beginIndex = 14; beginIndex < listLength - 14; beginIndex++) {
				dataPartList.add(strList.get(beginIndex));
			}

			int dataPartLength = dataPartList.size();
			int dataPartItem = dataPartLength / 10;

			for (int i = 0; i < dataPartItem; i++) {
				PositionCard pc = new PositionCard();
				pc.setCardNum(getByteHex(Integer.valueOf(dataPartList.get(0 + i * 10)))
						+ getByteHex(Integer.valueOf(dataPartList.get(1 + i * 10))));
				pc.setCardStatus(getByteHex(Integer.valueOf(dataPartList.get(2 + i * 10))));
				pc.setCompassNum(getByteHex(Integer.valueOf(dataPartList.get(3 + i * 10)))
						+ getByteHex(Integer.valueOf(dataPartList.get(4 + i * 10)))
						);
				pc.setIpAdress(ipAdress);
				pc.setTimeStamp(timeStamp);
				if(pc.getCardNum().startsWith("10")){
					pCardList.add(pc);
				}
				
			}
		}

		return pCardList;
	}

	/**
	 * 获得包编号
	 * @param strList
	 * @return
	 */
	private String[] getPackageNum(List<String> strList) {

		String[] packageNum = new String[] { "", "" };
		int length = strList.size();
		packageNum[0] = this.getByteHex(Integer.valueOf(strList.get(length - 10)));
		packageNum[1] = this.getByteHex(Integer.valueOf(strList.get(length - 9)));
		return packageNum;

	}

	/**
	 * 获得目标地址
	 * @param strList
	 * @return
	 */
	private String[] getTargetAddress(List<String> strList) {

		String[] targetAddressNum = new String[] { "", "" };
		int length = strList.size();
		targetAddressNum[0] = this.getByteHex(Integer.valueOf(strList.get(9)));
		targetAddressNum[1] = this.getByteHex(Integer.valueOf(strList.get(10)));
		return targetAddressNum;
	}
	
	

	/**
	 * 向每个客户端发送消息
	 * @return
	 */
	public  void sendConMessage(byte[] command , NioSocketAcceptor acceptor){
		
		IoSession session;
		
		Map conMap = acceptor.getManagedSessions();
		
		Iterator iter = conMap.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			session = (IoSession)conMap.get(key);
			
			session.write(IoBuffer.wrap(command));
		}

	}
	
	private String getIpAdd(String ipAdress){
		
		String ipStr[] = ipAdress.split(":");
		String ip = ipStr[0].substring(1);
		
		return ip;
		
	}
}
