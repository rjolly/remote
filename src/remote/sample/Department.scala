package remote.sample

import remote.Remote

class Department(val name: String) extends Serializable {
  override def toString = name
}

object Department {
  def apply(name: String)(implicit context: Remote[Company]) = for (obj <- context) yield new Department(name)

  implicit class Stub(val value: Remote[Department]) extends Remote.Stub[Department] {
    def name = (for (obj <- value) yield obj.name).get
  }
}
