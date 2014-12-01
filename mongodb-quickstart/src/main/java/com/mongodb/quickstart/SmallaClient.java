package com.mongodb.quickstart;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class SmallaClient {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongo = new MongoClient("10.66.218.46", 27017);
		DB db = mongo.getDB("mydb");
		DBCollection conn = db.getCollection("smalla");
		
//		findSearch();
		
		aggregateSearch(conn);
		
		mongo.close();
	}

	static void aggregateSearch(DBCollection collection) {
		if (collection != null) {
			ArrayList<DBObject> ops = new ArrayList<DBObject>();
			buildAggregate(ops, "$project", null);
			buildAggregate(ops, "$match", null); 
			buildAggregate(ops, "$group", new BasicDBObject().append("_id", new BasicDBObject("_c0", "$INTKEY")));
			buildAggregate(ops, "$match", null);
			buildAggregate(ops, "$project", new BasicDBObject("_m0", "$INTKEY"));
			buildAggregate(ops, "$sort", null); //$NON-NLS-1$
			buildAggregate(ops, "$skip", null); //$NON-NLS-1$
			buildAggregate(ops, "$limit", null);
			AggregationOutput output = collection.aggregate(ops.remove(0), ops.toArray(new DBObject[ops.size()]));
			Iterator<DBObject> cursor = output.results().iterator();
			while (cursor.hasNext()) {
				System.out.println(cursor.next().get("_m0"));
			}
		}
	}
	
	static void buildAggregate(List<DBObject> query, String type, Object object) {
		if (object != null) {
			query.add(new BasicDBObject(type, object));
		}
	}

	static void findSearch(DBCollection conn) throws UnknownHostException {

		DBCursor cursor = conn.find();
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

}
