package remote.client

import remote.Remote
import java.util.List

object MyApp extends App {
  val obj = Remote.lookup[List[String]]("obj")
  for (list <- obj) yield list.add("toto")
  for (list <- obj) yield println(list)
  println(obj.get)
}
