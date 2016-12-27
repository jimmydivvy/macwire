package com.softwaremill.macwire.macwireakka

import akka.actor.{Actor, ActorSystem, Props}

object Demo extends App {
  import java.util.concurrent.atomic.AtomicInteger

  import akka.actor.{Actor, ActorSystem, Props}
  import com.softwaremill.macwire.macwireakka._

  /**
    * In this example I am actor has 3 constructors but. `wireProps` will find dependencies for the constructor annotated
    * with @Inject and provide it's arguments to Props factory method.
    */

  trait A
  trait B
  trait C
  trait D

  class SomeActor(a: A) extends Actor {

    def this(b: B) = {
      this(new A{})
      throw new UnsupportedOperationException()
    }

    @javax.inject.Inject
    def this(c: C) = this(new A{})

    override def receive: Receive = {
      case m => //println(m)
    }
  }

  lazy val a: A = throw new UnsupportedOperationException()
  lazy val b: A = throw new UnsupportedOperationException()
  val c = new C {}

  val system = ActorSystem("wireProps-5-injectAnnotation")

  val props: Props = wireProps[SomeActor]

  val someActor = system.actorOf(props, "someActor")
  try {
    someActor ! "Hey someActor"
  } finally system.terminate()

}