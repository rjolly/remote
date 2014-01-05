package remote.sample

import remote.Remote

class Employee(val name: String,
               var department: Department,
               var location: String,
               var salary: Double,
               var manager: Employee,
               var job: String) extends Ordered[Employee] with Serializable {
  override def toString = name + " (" + department + ")"
  def compare(that: Employee) = this.name compare that.name
}

object Employee {
  implicit class Stub(val value: Remote[Employee]) extends Remote.Stub[Employee] {
    def name = (for (obj <- value) yield obj.name).get
    def department = for (obj <- value) yield obj.department
    def department_=(dep: Remote[Department]) = (for (obj <- value; department <- dep) yield obj.department = department).get
    def location = (for (obj <- value) yield obj.location).get
    def location_=(location: String) = (for (obj <- value) yield obj.location = location).get
    def salary = (for (obj <- value) yield obj.salary).get
    def salary_=(salary: Double) = (for (obj <- value) yield obj.salary = salary).get
    def manager = for (obj <- value) yield obj.manager
    def manager_=(mng: Remote[Employee]) = (for (obj <- value; manager <- mng) yield obj.manager = manager).get
    def job = (for (obj <- value) yield obj.job).get
    def job_=(job: String) = (for (obj <- value) yield obj.job = job).get
  }
}
