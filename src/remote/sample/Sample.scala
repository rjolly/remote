package remote.sample

import remote.Remote
import java.text.NumberFormat
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.iterableAsScalaIterable

object Sample {
  val obj = Remote.lookup[Company]("obj")

  def populate = for (company <- obj) yield {
    val accounting = company.createDepartment("Accounting")
    val research = company.createDepartment("Research")
    val sales = company.createDepartment("Sales")

    val clark = company.createEmployee("Clark", accounting, "New York", 29400.0, null, "Manager")
    val king = company.createEmployee("King", accounting, "New York", 60000.0, null, "President")
    val miller = company.createEmployee("Miller", accounting, "New York", 15600.0, null, "Clerk")
    val smith = company.createEmployee("Smith", research, "New York", 11400.0, null, "Clerk")
    val adams = company.createEmployee("Adams", research, "New York", 11400.0, null, "Clerk")
    val ford = company.createEmployee("Ford", research, "New York", 36000.0, null, "Analyst")
    val scott = company.createEmployee("Scott", research, "New York", 36000.0, null, "Analyst")
    val jones = company.createEmployee("Jones", research, "New York", 35700.0, null, "Manager")
    val allen = company.createEmployee("Allen", sales, "New York", 16800.0, null, "Salesman")
    val blake = company.createEmployee("Blake", sales, "New York", 34200.0, null, "Manager")
    val martin = company.createEmployee("Martin", sales, "New York", 16800.0, null, "Salesman")
    val james = company.createEmployee("James", sales, "New York", 11400.0, null, "Clerk")
    val turner = company.createEmployee("Turner", sales, "New York", 16800.0, null, "Salesman")
    val ward = company.createEmployee("Ward", sales, "New York", 16800.0, null, "Salesman")

    king.manager = king
    jones.manager = king
    scott.manager = jones
    adams.manager = scott
    ford.manager = jones
    smith.manager = ford
    blake.manager = king
    allen.manager = blake
    ward.manager = blake
    martin.manager = blake
    turner.manager = blake
    james.manager = turner
    clark.manager = king
    miller.manager = clark

    import company.employees
    employees.clear()
    List(clark, king, miller, smith, adams, ford, scott, jones, allen, blake, martin, james, turner, ward) foreach {
      employees.add(_)
    }
  }

  def average = for (company <- obj) yield {
    import company.employees
    val n = employees.size
    val s = employees.map(_.salary).sum
    company.createEmployee(name = "", salary = s / n)
  }

  val format = NumberFormat.getCurrencyInstance()

  def query(avg: Remote[Employee]) = (for (comp <- Lazy(obj); fmt <- Lazy(format)) yield {
    for (average <- avg; company <- comp) yield {
      import company.employees
      for (e <- employees if e.salary < average.salary) println(e + " " + fmt.format(e.salary))

      val bySalary: java.util.SortedSet[Employee] = new java.util.TreeSet[Employee](SalaryOrdering)
      bySalary.addAll(employees)

      println()

      for (e <- bySalary.tailSet(average)) println(e + " " + fmt.format(e.salary))
    }
  }).get

  def select(name: String) = for (company <- obj) yield {
    import company.employees
    employees.find(_.name == name).orNull
  }

  def setManager(emp1: Remote[Employee], emp2: Remote[Employee]) = {
    for (e2 <- emp2; e1 <- emp1) yield {
      e1.manager = e2
    }
  }

  def setSalary(emp: Remote[Employee], value: Double) = {
    for (e <- emp) yield {
      e.salary = value
    }
  }
}
