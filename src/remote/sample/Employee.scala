package remote.sample

case class Employee(name: String,
                    var department: Department = null,
                    var location: String = "",
                    var salary: Double = 0.0,
                    var manager: Employee = null,
                    var job: String = "") extends Ordered[Employee] {
  override def toString = name + " (" + department + ")"
  def compare(that: Employee) = this.name compare that.name
}
