package remote.client

import remote.Remote
import java.util.List

object MyApp extends App {
  val obj = java.rmi.Naming.lookup("obj").asInstanceOf[Remote[List[String]]]
  for (list <- obj) yield list.add("toto")
  for (list <- obj) yield println(list)
  println(obj.get)
}
