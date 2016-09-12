package com.bizruntime.config.repo


import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import com.bizruntime.config.repo.filerepo.ConfigFileRepoSupActor
import com.bizruntime.config.api.AddConfigurationMsg
import com.bizruntime.config.api.ConfigurationRequestMsg
import com.bizruntime.config.api.UpdateConfigurationMsg
import com.bizruntime.config.api.DeleteConfigurationMsg

class ConfigurationSupActor extends Actor {
  val log = Logging(context.system, this)
  val value = ConfigFactory.load().getString("rconfig.repositoryType")
  log debug s"Starting ConfigServer with RepositoryType $value"
  val configRepoActorRef =repoSupActor
  
  def receive:Receive = {
    case msg @ ( ConfigurationRequestMsg|AddConfigurationMsg | UpdateConfigurationMsg | DeleteConfigurationMsg) => configRepoActorRef forward msg
    case _ => sender ! "This Msg is not Supported"
  }
  
 def repoSupActor=value match{
   case _ => context.actorOf(Props[ConfigFileRepoSupActor], "ConfigFileRepoSupActor")
 }
}

