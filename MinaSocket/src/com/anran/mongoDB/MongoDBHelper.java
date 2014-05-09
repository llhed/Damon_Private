package com.anran.mongoDB;



import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoDBHelper {
	
	public DBCollection dbCollection;
	public MongoDBCollectionFactory mdbf;

	public void setMdbf(MongoDBCollectionFactory mdbf) {
		this.mdbf = mdbf;
	}


	public MongoDBHelper() {
		super();
	}

	public MongoDBHelper(String collectionName ){
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		MongoDBCollectionFactory mdbf = (MongoDBCollectionFactory) ctx.getBean("mongoCollection");
		
		this.dbCollection = this.mdbf.getCollection(collectionName);
	}

	public static void addSigleDate(){
		
		DBObject user = new BasicDBObject();  
		MongoDBHelper mdb = new MongoDBHelper("0532");
		
		user.put("value", 23.4);
		user.put("timeStamp", 456);
		user.put("sensor", 0532);
		
		mdb.dbCollection.insert(user);
	}
	
	
	public void addListData(){
		
	}
	
//	public static void main( String[] args ) throws IOException 
//	{ 
//		addSigleDate();
//	}
}
