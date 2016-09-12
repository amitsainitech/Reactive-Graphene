package com.bizruntime.config.service

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class ConfigurationServiceSupActor extends Actor {
  val log = Logging(context.system, this)
  val fileStoreConfigARef = context.actorOf(Props[FileStoreConfigurationActor], "fileStoreConfig")
  
  def receive:Receive = {
    case fetchCmd: FetchConfigurationCmd => fileStoreConfigARef.forward(fetchCmd)
    case addCmd: AddConfigurationCmd => fileStoreConfigARef.forward(addCmd)

  }
}