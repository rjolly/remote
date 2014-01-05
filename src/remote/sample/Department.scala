package remote.sample

import remote.Remote

class Department(val name: String) extends Serializable {
  override def toString = name
}

object Department {
  implicit class Stub(val value: Remote[Department]) extends Remote.Stub[Department] {
    def name = (for (obj <- value) yield obj.name).get
  }
}
