package remote.sample

import remote.Remote
import java.util.ArrayList

class Company extends Serializable {
  val employees: java.util.Collection[Employee] = new ArrayList[Employee]

  def createEmployee(name: String,
                     department: Department = null,
                     location: String = "",
                     salary: Double = 0.0,
                     manager: Employee = null,
                     job: String = "") = new Employee(name, department, location, salary, manager, job)

  def createDepartment(name: String) = new Department(name)
}

object Company {
  implicit class Stub(val value: Remote[Company]) extends Remote.Stub[Company] {
    def employees = for (obj <- value) yield obj.employees

    def createEmployee(name: String,
                       dep: Remote[Department] = null,
                       location: String = "",
                       salary: Double = 0.0,
                       mng: Remote[Employee] = null,
                       job: String = "") = {
      if (dep == null) {
        if (mng == null) (for (obj <- value) yield obj.createEmployee(name = name, location = location, salary = salary, job = job)).get
        else (for (obj <- value; manager <- mng) yield obj.createEmployee(name = name, location = location, salary = salary, manager = manager, job = job)).get
      } else {
        if (mng == null) (for (obj <- value; department <- dep) yield obj.createEmployee(name = name, department = department, location = location, salary = salary, job = job)).get
        else (for (obj <- value; department <- dep; manager <- mng) yield obj.createEmployee(name, department, location, salary, manager, job)).get
      }
    }

    def createDepartment(name: String) = (for (obj <- value) yield obj.createDepartment(name)).get
  }
}
