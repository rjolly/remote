package remote.sample

import java.util.{ArrayList, Collection}

class Company extends Serializable {
  val employees: Collection[Employee] = new ArrayList[Employee]

  def createEmployee(name: String,
                     department: Department = null,
                     location: String = "",
                     salary: Double = 0.0,
                     manager: Employee = null,
                     job: String = "") = new Employee(name, department, location, salary, manager, job)
  def createDepartment(name: String) = new Department(name)
}
