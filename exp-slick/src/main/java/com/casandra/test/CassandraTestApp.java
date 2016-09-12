package com.casandra.test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class CassandraTestApp {
	private Cluster cluster;

	   public void connect(String node) {
	      cluster = Cluster.builder().addContactPoint(node).build();
	      Metadata metadata = cluster.getMetadata();
	      System.out.println("Connected to cluster: ");
//	      for ( Host host : metadata.getAllHosts() ) {
//	         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
//	               host.getDatacenter(), host.getAddress(), host.getRack());
//	      }
	   }

	   public void close() {
	      cluster.close();
	   }

	   public static void main(String[] args) {
		   CassandraTestApp client = new CassandraTestApp();
	      client.connect("192.168.1.172");
	      client.close();
	   }
}
