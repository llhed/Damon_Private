package com.anran.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.anran.pojo.T_SENSOR_IMM;
import com.anran.services.NodeServices;
import com.anran.services.PersonServices;
import com.anran.services.T_Sensor_ImmServices;
import com.anran.servicesImpl.PersonServicesImpl;



public class DBTest {

	
	
	@Test
	public void getByteHex(){
		
		String strHex = Integer.toHexString(-18);
		String result = strHex.substring(strHex.length()-2, strHex.length());
		
		System.out.print("");
	}
	
	public static void main( String[] args ) throws IOException
	{ 
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonServices ps = (PersonServices) ctx.getBean("personServices");
		
		String count = ps.getStationPersonLimit("192.168.1.203");
		System.out.print("123");
		
//		List<T_SENSOR_IMM> dataBeenList = new ArrayList<T_SENSOR_IMM>();
//		T_SENSOR_IMM dataBeen1 = new T_SENSOR_IMM();
//		T_SENSOR_IMM dataBeen2 = new T_SENSOR_IMM();
//		
//		dataBeen1.setSensor_Num("08005");
//		dataBeen1.setValue("500");
//		dataBeen1.setTime("20140211130000");
//		
//		dataBeen2.setSensor_Num("08006");
//		dataBeen2.setValue("600");
//		dataBeen2.setTime("20140211130000");
//		
//		dataBeenList.add(dataBeen1);
//		dataBeenList.add(dataBeen2);
		
		
//		NodeServices nodeservice = (NodeServices) ctx.getBean("nodeServicesImpl");
//		
//		List<String> strList = new ArrayList<String>();
//		strList.add("05001");
//		strList.add("05002");
//		nodeservice.getNodeInfo(strList);
//		T_Sensor_ImmServices sensor_ImmServices = (T_Sensor_ImmServices)ctx.getBean("t_Sensor_ImmServices");
		
//		sensor_ImmServices.updataBatchData(dataBeenList);
		//sensor_ImmServices.insertBatchData(dataBeenList);
		
	}
	
	@Test
	public void toHex(){
		
		String result = Integer.toHexString(-108);
		System.out.print(result);
	}

//	PersonLocationImm pli = new PersonLocationImm();
//	pli.setPersonNum("00001");
//	pli.setNodeIdIndex("05001");
//	personLocationImmServices.insertPersonLocation(pli);
	
//	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//	String[] str = message.toString().split("&");
//	Date timeStamp = new Date();
//	DateBean dbBean = new DateBean();
//	try{
//		
//
//		DBObject user = new BasicDBObject();
//		
//		user.put("sensor", str[0]);
//		user.put("value", str[1]);
//		user.put("unit", str[2]);
//		user.put("timestamp", Long.valueOf(df.format(timeStamp)));
//		
//		dbBean.setSensor(str[0]);
//		dbBean.setValue(str[1]);
//		dbBean.setUnit(str[2]);
//		dbBean.setTimeStamp(Long.valueOf(df.format(timeStamp)));
//		
//		mongoTemplate.insert(collectionName, dbBean);
//		
//
//		System.out.println(str[0] + " " + str[1] + " " + str[2]);
//		System.out.println(df.format(timeStamp));		
//
//		
//	}catch(Exception e){
//		e.printStackTrace();
//	}
//
	@Test
	public void TestList(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonServices ps = (PersonServices) ctx.getBean("personServices");
		String count = ps.getStationPersonLimit("192.168.1.203");
		System.out.print(count);
	}
}
