package remote.sample

import remote.Remote
import java.util.ArrayList

object Server extends App {
  Remote.rebind("obj", new ArrayList[Employee])
  println("obj bound in registry")
}
