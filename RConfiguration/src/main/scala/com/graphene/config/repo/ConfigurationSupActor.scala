package com.graphene.config.repo


import akka.actor.Actor
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import com.graphene.config.api.AddConfigurationMsg
import com.graphene.config.api.ConfigurationRequestMsg
import com.graphene.config.api.UpdateConfigurationMsg
import com.graphene.config.api.DeleteConfigurationMsg
import akka.actor.actorRef2Scala
import akka.actor.Props
import com.graphene.config.repo.filerepo.ConfigFileRepoSupActor

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

