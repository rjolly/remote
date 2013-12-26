package remote.server

import remote.Remote
import java.util.ArrayList

object Server extends App {
  val obj = Remote(new ArrayList[String])
  java.rmi.Naming.rebind("obj", obj)
  println("obj bound in registry")
}
