package com.bizruntime.config

import akka.actor.ActorSystem
import akka.actor.Props
import com.bizruntime.config.service.ConfigurationServiceSupActor
import com.bizruntime.config.service.AddConfigurationCmd
import com.typesafe.config.ConfigFactory
import com.bizruntime.config.service.FileRepoUtil

object ConfigurationTestApp extends App {

  val system = ActorSystem("ConfigurationApp");
  val configServARef = system.actorOf(Props[ConfigurationServiceSupActor], "ConfigService")

  val conf = ConfigFactory.load();
  println(conf.getString("RepositoryBaseDir"))
    
  val addConfigCmd = AddConfigurationCmd("dumyTennt", "testUser", "app1::ms1", "myapp.xslt", "my xslt will be stored here")
  configServARef.tell(addConfigCmd, null)
  println("Msg sent");
  
  
  //FileRepoUtil.writeConfig("D:/Learning-Space/Scala/configRepo/t1/app1","abc.xslt", "2myxslt is as follows")
}