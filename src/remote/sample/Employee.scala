package remote.sample

class Employee(val name: String,
               var department: Department,
               var location: String,
               var salary: Double,
               var manager: Employee,
               var job: String) extends Ordered[Employee] with Serializable {
  override def toString = name + " (" + department + ")"
  def compare(that: Employee) = this.name compare that.name
}
