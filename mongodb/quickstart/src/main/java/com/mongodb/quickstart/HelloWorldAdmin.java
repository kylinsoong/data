package com.mongodb.quickstart;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class HelloWorldAdmin {

	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient mongo = new MongoClient("localhost", 27017);
		
		for (String s : mongo.getDatabaseNames()) {
            System.out.println(s);
        }
		
		DB db = mongo.getDB("com_mongodb_MongoAdmin");
		db.getCollection("testcollection").insert(new BasicDBObject("i", 1));
        for (String s : mongo.getDatabaseNames()) {
            System.out.println(s);
        }
        
        mongo.dropDatabase("com_mongodb_MongoAdmin");
        
        for (String s : mongo.getDatabaseNames()) {
            System.out.println(s);
        }
        
        mongo.close();
	}

}
