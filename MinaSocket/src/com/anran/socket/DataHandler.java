package com.anran.socket;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.aspectj.weaver.Position;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.anran.mongoDB.MongoDBCollectionFactory;
import com.anran.mongoDB.MongoDBHelper;
import com.anran.pojo.DateBean;
import com.anran.pojo.DeviceWorkImm;
import com.anran.pojo.PersonLocationImm;
import com.anran.pojo.PositionCard;
import com.anran.services.DeviceWorkImmServices;
import com.anran.services.PersonLocationImmServices;
import com.anran.util.PositionCardCommand;
import com.anran.util.StringTransfer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 
 * @author Administrator
 * 
 */
public class DataHandler extends IoHandlerAdapter {

	// private Logger logger = Logger.getLogger(MinaTimeServer.class);
	private MongoDBHelper mdb;
	private MongoDBCollectionFactory mdbf;
	private MongoTemplate mongoTemplate;
	private DeviceWorkImmServices deviceWorkImmServices;
	private PersonLocationImmServices personLocationImmServices;

	public void setPersonLocationImmServices(PersonLocationImmServices personLocationImmServices) {
		this.personLocationImmServices = personLocationImmServices;
	}

	public void setDeviceWorkImmServices(DeviceWorkImmServices deviceWorkImmServices) {
		this.deviceWorkImmServices = deviceWorkImmServices;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void setMdbf(MongoDBCollectionFactory mdbf) {
		this.mdbf = mdbf;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

		System.out.println("remote client [" + session.getRemoteAddress().toString() + "] connected.");
		

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

//		byte[] aaa = { (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xaa, (byte) 0x0f, (byte) 0x00,
//				(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0x06,
//				(byte) 0x13, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xd1, (byte) 0xe0 };
		byte[] readAllStation = PositionCardCommand.READ_POSITION_STATION;
		
		session.write(IoBuffer.wrap(readAllStation));
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// cause.printStackTrace();
	}

	/**
	 * 收到Heap堆栈缓存区数据
	 */
	@Override
	public void messageReceived(IoSession session, Object message) {
		
		List<PositionCard> pCardList = new ArrayList();
		
		List<String> dataStrList = new ArrayList();
		
		if(message != null){
			dataStrList = ananlaysBuf(message);
			if(dataStrList.size() > 28){
				pCardList = wrapCardData(dataStrList , session);
			}
			
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 从缓存中读取字节码并进行转码
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
	 * 获得单字节的16进制字符串
	 * @param intByte
	 * @return
	 */
	private String getByteHex(int intByte) {

		String strHex = Integer.toHexString(intByte);
		if( intByte >0 ){
			return strHex.length() == 1 ? "0"+strHex : strHex;
		}else{
			return strHex.substring(strHex.length() - 2, strHex.length());
		}
		
	}
	
	/**
	 * 对识别卡数据进行对象化封装
	 * @param strList
	 * @return
	 */
	private List<PositionCard> wrapCardData(List<String> strList , IoSession session){
		
		List<PositionCard> cardList = new ArrayList();
		PositionCard pCar = new PositionCard();
		int listLength = strList.size();
		StringBuilder sbTime = new StringBuilder();
		
		String ipAdress = session.getRemoteAddress().toString().split(":")[0].substring(1);
		String timeStamp = wrapDataTime(sbTime , strList , listLength);
		
		cardList = getObjectWrap(ipAdress , timeStamp , strList , listLength);
	
		System.out.println(cardList.size());
		return cardList;
	}
	
	/**
	 * 封装时间字符串 如："2014-06-23 08:00:00"
	 * @param sbTime
	 * @param strList
	 * @param listLength
	 * @return
	 */
	private String wrapDataTime(StringBuilder sbTime , List<String> strList , int listLength){
		
		sbTime.append("20");
		sbTime.append(strList.get(listLength-8));
		sbTime.append("-");
		sbTime.append(strList.get(listLength-7));
		sbTime.append("-");
		sbTime.append(strList.get(listLength-6));
		sbTime.append(" ");
		
		sbTime.append(strList.get(listLength-5));
		sbTime.append(":");
		sbTime.append(strList.get(listLength-4));
		sbTime.append(":");
		sbTime.append(strList.get(listLength-3));
		
		return sbTime.toString();
	}

	/**
	 * 获取数据包的DATA部分，进行对象封装
	 * @param ipAdress   IP地址
	 * @param timeStamp  时间戳
	 * @param strList    数据包String泛型数组
	 * @param listLength 数据包长度
	 * @return
	 */
	private List<PositionCard> getObjectWrap(String ipAdress , String timeStamp , List<String> strList , int listLength){
		
		List<PositionCard> pCardList = new ArrayList();
		
		List<String> dataPartList = new ArrayList();
		
		//确保数据包至少有一条记录
		if(listLength > 28){
			
			for(int beginIndex = 14 ; beginIndex < listLength - 14 ; beginIndex++){
			dataPartList.add(strList.get(beginIndex));
			} 
			
			int dataPartLength = dataPartList.size();
			int dataPartItem = dataPartLength/10;
			
			for(int i= 0 ; i < dataPartItem ; i++){
				PositionCard pc = new PositionCard();
				pc.setCardNum(this.getByteHex(Integer.valueOf(dataPartList.get(0+i*10))) + this.getByteHex(Integer.valueOf(dataPartList.get(1+i*10))));
				pc.setCardStatus(this.getByteHex(Integer.valueOf(dataPartList.get(2+i*10))));
				pc.setIpAdress(ipAdress);
				pc.setTimeStamp(timeStamp);
				
				pCardList.add(pc);
			}
		}
		
		return pCardList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
