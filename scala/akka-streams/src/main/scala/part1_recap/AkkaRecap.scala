package part1_recap

import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, PoisonPill, Props, Stash, SupervisorStrategy}
import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.util.Timeout

import scala.language.postfixOps

object AkkaRecap extends App {
  private class SimpleActor extends Actor with ActorLogging with Stash {
    override def receive: Receive = {
      case "createChild" =>
        val childActor = context.actorOf(Props(classOf[SimpleActor]), "myChild")
        childActor ! "hello"
      case "stashThis" =>
        stash()
      case "change handler NOW" =>
        unstashAll()
        context.become(anotherHandler)
      case "change" => context.become(anotherHandler)
      case message => println(s"I received: $message")
    }
    private def anotherHandler: Receive = {
      case message => println(s"Another receive handler: $message")
    }

    override def preStart(): Unit = {
      log.info("I'm starting")
    }

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case _: RuntimeException => Restart
      case _ => Stop
    }
  }

  // actor encapsulation
  private val system = ActorSystem("AkkaRecap")
  // #1: you can only instantiate an actor through the actor system
  private val actor = system.actorOf(Props(classOf[SimpleActor]), "simpleActor")
  // #2: sending messages
  actor ! "hello"
  /*
  - messages are sent asynchronously
  - many actors (in the millions) can share a few dozen threads
  - each message is processed / handled ATOMICALLY
  - no need for locks
   */

  // changing actor
  // actors can spawn other actors
  // guardians: /system, /user, / = root guardian

  // actors have a defined lifecycle: they can be started, stopped, suspended, resumed, restarted

  // stopping actors - context.stop
  actor ! PoisonPill

  // logging
  // supervision

  // configure Akka Infrastructure: dispatchers, routers, mailboxes

  // schedulers
  import scala.concurrent.duration._
  import system.dispatcher
  system.scheduler.scheduleOnce(2 seconds) {
    actor ! "delayed Happy Birthday"
  }

  // Akka patterns including Finite State Machines + ask pattern
  import akka.pattern.ask
  implicit val timeout: Timeout = Timeout(3 seconds)

  private val future = actor ? "question"

  // the pipe pattern
  import akka.pattern.pipe
  private val anotherActor = system.actorOf(Props(classOf[SimpleActor]), "anotherSimpleActor")
  future.mapTo[String].pipeTo(anotherActor)
}
