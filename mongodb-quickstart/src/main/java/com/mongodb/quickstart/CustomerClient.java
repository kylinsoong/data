package com.mongodb.quickstart;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class CustomerClient {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongo = new MongoClient("10.66.218.46", 27017);
		DB db = mongo.getDB("mydb");
		
		DBCollection conn = db.getCollection("Customer");
		
//		conn.drop();
		
		DBCursor cursor = conn.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
        mongo.close();
	}

}
