package remote.sample

import remote.Remote
import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

object Server extends App {
  val file = new File("remote.ser")
  val company = if (file.exists) {
    println("reading state")
    val is = new ObjectInputStream(new FileInputStream(file))
    val obj = is.readObject()
    is.close
    obj.asInstanceOf[Company]
  } else new Company
  Remote.rebind("obj", company)
  println("obj bound in registry")
  Runtime.getRuntime().addShutdownHook(new Thread {
    override def run = {
      println("writing state")
      val os = new ObjectOutputStream(new FileOutputStream(file))
      os.writeObject(company)
      os.close
    }
  })
}
