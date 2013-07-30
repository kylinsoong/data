package com.mongodb.quickstart;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class HelloWorld {
	
	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("mydb");
		
		Set<String> collectionNames = db.getCollectionNames();
        for (String s : collectionNames) {
            System.out.println(s);
        }
        
        DBCollection conn = db.getCollection("testCollection");
        
        conn.drop();
        
		BasicDBObject doc = new BasicDBObject().append("name", "MongoDB")
				.append("type", "database").append("count", 1)
				.append("info", new BasicDBObject("x", 203).append("y", 102));
		
        conn.insert(doc);
        
        DBObject myDoc = conn.findOne();

		System.out.println(myDoc);
		
		for (int i = 0; i < 100; i++) {
            conn.insert(new BasicDBObject().append("i", i));
        }
        System.out.println("total # of documents after inserting 100 small ones (should be 101) " + conn.getCount());

		// now use a query to get 1 document out
        DBCursor cursor = conn.find();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
		// now use a range query to get a larger subset
        BasicDBObject query = new BasicDBObject("i", 71);
        cursor = conn.find(query);

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
		// range query with multiple constraints
        query = new BasicDBObject("i", new BasicDBObject("$gt", 20).append("$lte", 30)); // i.e. 20 < i <= 30
        cursor = conn.find(query);
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
		// now use a range query to get a larger subset
        query = new BasicDBObject("i", new BasicDBObject("$gt", 50)); // i.e. find all where i > 50
        cursor = conn.find(query);

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
		// create an index on the "i" field
        conn.createIndex(new BasicDBObject("i", 1)); // create index on "i", ascending
        
		// list the indexes on the collection
        List<DBObject> list = conn.getIndexInfo();
        for (DBObject o : list) {
            System.out.println(o);
        }
        
		// See if the last operation had an error
        System.out.println("Last error : " + db.getLastError());
        
		// see if any previous operation had an error
        System.out.println("Previous error : " + db.getPreviousError());
        
		// force an error
        db.forceError();

        // See if the last operation had an error
        System.out.println("Last error : " + db.getLastError());
        
        db.resetError();
        
        mongo.close();
	}
}
