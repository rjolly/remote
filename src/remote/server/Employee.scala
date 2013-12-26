package remote.server

case class Employee(name: String, department: Department, location: String, salary: Double, job: String) extends Ordered[Employee] {
  var manager: Employee = _
  override def toString = name + " (" + department + ")"
  def compare(that: Employee) = this.name compare that.name
}
