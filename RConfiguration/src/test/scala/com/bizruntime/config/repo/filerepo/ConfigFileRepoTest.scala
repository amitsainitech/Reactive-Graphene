package com.bizruntime.config.repo.filerepo
import scala.util.Random
 
import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
 
import com.typesafe.config.ConfigFactory
 
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.{ TestActors, DefaultTimeout, ImplicitSender, TestKit }
import scala.concurrent.duration._
import scala.collection.immutable
import org.junit.runner.RunWith
import akka.testkit.TestProbe
import com.bizruntime.core.MSNameSpace
import com.bizruntime.config.api.AddConfigurationMsg
import com.bizruntime.config.api.ConfigurationAddedResMsg
import com.bizruntime.config.api.ConfigurationRequestMsg
import com.bizruntime.config.api.ConfigurationResponseMsg


class ConfigFileRepoTest extends TestKit(ActorSystem(
    "ConfigFileRepoTest",
    ConfigFactory.parseString(ConfigFileRepoTest.config)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

   val echoRef = system.actorOf(TestActors.echoActorProps)
   val addConfigARef = system.actorOf(Props(classOf[ConfigurationAddActor]))
    val getConfigARef = system.actorOf(Props(classOf[ConfigurationRequestActor]))
   
   override def afterAll {
    shutdown()
  }
   
   val ns=new MSNameSpace("testMS","v1","TestImpl",List(("test","test")))
   
   "An EchoActor" should {
    "Respond with the same message it receives" in {
      within(500 millis) {
        echoRef ! "test"
         expectMsg("test")
      }
    }
  }
   
    "Add Config to Actor" should {
    "Respond with ConfigurationResponseMsg" in {
      within(500 millis) {
        getConfigARef ! ConfigurationRequestMsg("test",ns,"test1",".jconf")
       expectMsgType[ConfigurationResponseMsg]
      }
    }
  }

    "Get Config to Actor" should {
    "Respond with ConfigurationAddedResMsg" in {
      within(500 millis) {
        addConfigARef ! AddConfigurationMsg("test",ns,"test1","MyConfig Value",".jconf")
       expectMsgType[ConfigurationAddedResMsg]
      }
    }
  }
}

object ConfigFileRepoTest {
  // Define your test specific configuration here
  val config = """
    akka {
      loglevel = "WARNING"
    }
    """
}