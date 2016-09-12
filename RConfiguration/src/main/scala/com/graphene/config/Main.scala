package com.graphene.config

import com.graphene.config.repo.ConfigurationSupActor
import akka.actor.Props
import akka.actor.ActorSystem

object Main extends App {
  val system = ActorSystem.create("BizRconfiguration");
  system.actorOf(Props[ConfigurationSupActor], "Configuration-Supervisor");
}