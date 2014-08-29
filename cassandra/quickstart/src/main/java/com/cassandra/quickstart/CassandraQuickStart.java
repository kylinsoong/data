package com.cassandra.quickstart;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;

public class CassandraQuickStart {
	
	private Cluster cluster = null;
	private Session session = null;
	private Metadata metadata = null;
	
	private String username = null;
	private String password = null;
	private Integer port = 9042;
	private String address = "10.66.218.46";
	private String keyspace = "demo";
	
	public CassandraQuickStart() {
		
		Cluster.Builder builder  = Cluster.builder().addContactPoint(address).withRetryPolicy(DefaultRetryPolicy.INSTANCE).withPort(port);
		
		if(null != username && null != password) {
			builder.withCredentials(username, password);
		}
		
		this.cluster = builder.build();
		this.metadata = cluster.getMetadata();
		this.session = cluster.connect(keyspace);
	}
	
	public void test() {
		session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Soong', 29, 'Beijing', 'ksoong@redhat.com', 'Kylin')") ;
		ResultSet results = session.execute("SELECT * FROM users");
		for (Row row : results) {
			System.out.format("%s %d\n", row.getString("firstname"),row.getInt("age"));
		}
		session.execute("DELETE FROM users WHERE lastname = 'Soong'");
		
	}

	public static void main(String[] args) {

		CassandraQuickStart quickstart = new CassandraQuickStart();
		quickstart.test();
		System.out.println("DONE");
	}
}
