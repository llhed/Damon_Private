package com.anran.mongoDB;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBCollectionFactory {

	
	private String ip;
	private String dbName;
	private int port;
	private DBCollection dbCollection;
	
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}	
	public void setPort(int port) {
		this.port = port;
	}
	
	
	public void setDbCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}
	
	@SuppressWarnings("deprecation")
	public DBCollection getCollection(String collectionName){
		
		try {
			
			Mongo mongo = new Mongo(this.ip , this.port);
			DB db = mongo.getDB(this.dbName);
			
			dbCollection = db.getCollection("test");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbCollection;
	}
}
