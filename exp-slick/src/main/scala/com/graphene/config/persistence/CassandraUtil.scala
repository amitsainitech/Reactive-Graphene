package com.graphene.config.persistence

 import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session

object CassandraUtil {
  val keyspace="notificationKS";
  
  def getConnection(cassandraNode:String):Session ={
    val cluster=Cluster.builder().addContactPoint(cassandraNode).build();
    val session = cluster.connect
    session.execute(s"USE ${keyspace}")
    session
  }
  
  
}