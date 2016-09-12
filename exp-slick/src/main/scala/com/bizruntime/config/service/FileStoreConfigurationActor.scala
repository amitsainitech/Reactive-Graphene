package com.bizruntime.config.service

import akka.event.Logging
import akka.actor.Actor
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import java.io.File
import akka.actor.Props


class FileStoreConfigurationActor extends Actor {
  val log = Logging(context.system, this)
  val repoBaseDir: String = ConfigFactory.load().getString("RepositoryBaseDir");
   context.actorOf(FileStoreConfigFetchActor props repoBaseDir , "fileStoreConfigFetch")
   
  def receive: Receive = {
    case addCmd: AddConfigurationCmd => addConfiguration(addCmd)
    case _ => {
      log.debug("Log Some msg Received");
      println("Some msg Received");
    }
  }

  //handeles AddConfigMsges
  def addConfiguration(configCmd: AddConfigurationCmd) {
    val nsPath = configCmd.configNameSpace.replaceAll("::", "/")
    val finalPath = repoBaseDir + "/" + configCmd.tenant + "/" + nsPath;
    FileRepoUtil.writeConfig(finalPath, configCmd.configName, configCmd.content)
    log.debug(s"addConfiguration. finalPath=$finalPath")
  }
  
} //end of Actor

object FileStoreConfigFetchActor {
	def props(repoDir: String): Props = {
			Props(classOf[FileStoreConfigFetchActor], repoDir)
	}
}
class FileStoreConfigFetchActor(repoDir: String) extends Actor {
  val log = Logging(context.system, this)
  def receive: Receive = {
    case fetchConfigCmd: FetchConfigurationCmd => getStaticConfigPath(fetchConfigCmd)
    case _ => {
      log.debug("Log Some msg Received");
      println("Some msg Received");
    }
  }

  def getStaticConfigPath(configCmd: FetchConfigurationCmd) {
	val nsPath = configCmd.configNameSpace.replaceAll("::", "/")
    val finalPath = repoDir + "/" + configCmd.tenant + "/" + nsPath+""+configCmd.configName
	val contentFile = new File(finalPath)
	val isExist=contentFile.exists()
	isExist match{
	  case true => sender() ! FetchConfigResponse(configCmd,finalPath)
	  case _ => sender() ! ConfigCmdFailure(configCmd,"sc-001",s"Requested content not found")
	}
  }
}//end of Actor

