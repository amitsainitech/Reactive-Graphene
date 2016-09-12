package com.graphene.config.repo.filerepo

import java.io.PrintWriter

import scala.io.BufferedSource

import com.graphene.config.api.AddConfigurationMsg
import com.graphene.config.api.ConfigurationRequestMsg
import com.graphene.config.api.DeleteConfigurationMsg
import com.graphene.config.api.UpdateConfigurationMsg
import com.graphene.core.MSNameSpace
import com.graphene.core.SuccessReplyMsg
import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.event.Logging


class ConfigFileRepoSupActor extends Actor {
  val log = Logging(context.system, this)
  val reqActorRef = context.actorOf(Props[ConfigurationRequestActor], "ConfigurationRequestActor")
  val updateActorRef = context.actorOf(Props[ConfigurationUpdateActor], "ConfigurationUpdateActor")
  val addActorRef = context.actorOf(Props[ConfigurationAddActor], "ConfigurationAddActor")
  val deleteActorRef = context.actorOf(Props[ConfigurationDeleteActor], "ConfigurationDeleteActor")

  def receive: Receive = {
    case req: ConfigurationRequestMsg   => reqActorRef forward req
    case update: UpdateConfigurationMsg => updateActorRef forward update
    case delete: DeleteConfigurationMsg => deleteActorRef forward delete
    case add: AddConfigurationMsg       => addActorRef forward add
    case x                              => sender ! "Not Ready Yet"
  }
}

import scala.io._
import com.graphene.config.api.{ConfigurationResponseMsg,RequestedConfigurationNotFoundMsg}
import com.graphene.config.api.ConfigurationAddedResMsg

class ConfigurationRequestActor extends Actor {
  val baseDir = ConfigFactory.load().getString("rconfig.fileRepo.baseDir")
  val log = Logging(context.system, this)
  def receive: Receive = {
    case ConfigurationRequestMsg(tenantId:String,configNS:MSNameSpace,configName:String,contentType:String) =>{
             val configPath= FileRepoUtil.finalNSDir(baseDir, tenantId, configNS)+"/"+configName+"."+contentType
             if(FileRepoUtil.isFileExist(configPath)){
                sender ! ConfigurationResponseMsg(tenantId,ConfigurationRequestMsg(tenantId,configNS,configName,contentType),readConfigFile(configPath))
             }else{
               sender ! RequestedConfigurationNotFoundMsg(tenantId,ConfigurationRequestMsg(tenantId,configNS,configName,contentType),"Configuration Does not Exist")
             }
           }
  }
  
  def readConfigFile(configToRead:String):String={
     val bufferedSource = Source.fromFile(configToRead)
     val configString:String=bufferedSource.getLines.mkString
     bufferedSource.close
     configString
  }
}

class ConfigurationUpdateActor extends Actor { 
  val baseDir = ConfigFactory.load().getString("rconfig.fileRepo.baseDir")
  val log = Logging(context.system, this)
  def receive: Receive = {
    case x: UpdateConfigurationMsg => sender ! "Not Ready Yet"
  }
}

class ConfigurationDeleteActor extends Actor {
  val baseDir = ConfigFactory.load().getString("rconfig.fileRepo.baseDir")
  val log = Logging(context.system, this)
  def receive: Receive = {
    case x: DeleteConfigurationMsg => sender ! "Not Ready Yet"
  }
}


class ConfigurationAddActor extends Actor {
  val baseDir = ConfigFactory.load().getString("rconfig.fileRepo.baseDir")
  val log = Logging(context.system, this)
  def receive: Receive = {
    case AddConfigurationMsg(tenantId:String,configNS:MSNameSpace,configName:String,content:String,contentType:String) => {
          if(!FileRepoUtil.doesFinalNSDirExist(baseDir, tenantId, configNS))  FileRepoUtil.setupNSFolder(baseDir, tenantId, configNS)
          else{}
          writeContent(FileRepoUtil.finalNSDir(baseDir, tenantId, configNS)+"/"+configName+"."+contentType,content)
          log.debug(s"Configuration{$configName.$contentType} added to repo for tenant{$tenantId} with Namespace{$configNS}")
          sender ! new ConfigurationAddedResMsg("200",s"Configuration with $configName created sucessfully",tenantId)
    }
  }
  def writeContent(filePath:String,content:String)={
    new PrintWriter(filePath) { write(content); close }
  }
}

