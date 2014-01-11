package remote.sample

import remote.Remote
import java.util.ArrayList

class Company extends Serializable {
  val employees: java.util.Collection[Employee] = new ArrayList[Employee]
}

object Company {
  implicit class Stub(val value: Remote[Company]) extends Remote.Stub[Company] {
    def employees = for (obj <- value) yield obj.employees
  }
}
