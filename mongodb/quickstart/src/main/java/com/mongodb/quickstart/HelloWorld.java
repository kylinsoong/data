package com.mongodb.quickstart;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class HelloWorld {
	
	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("mydb");
		
		Set<String> collectionNames = db.getCollectionNames();
        for (String s : collectionNames) {
            System.out.println(s);
        }
		
		System.out.println(mongo);
	}
}
