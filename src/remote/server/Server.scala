package remote.server

import remote.Remote
import java.util.ArrayList

object Server extends App {
  Remote.rebind("obj", new ArrayList[String])
  println("obj bound in registry")
}
