package remote.client

import remote.{Remote, Lazy}
import remote.server.{Employee, Department, SalaryOrdering}
import java.text.NumberFormat
import java.util.{Collection, SortedSet, TreeSet}
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.asScalaIterator

object Sample extends App {
  val obj = Remote.lookup[Collection[Employee]]("obj")

  // Populate

  for (employees <- obj) yield {
    val accounting = Department("Accounting")
    val research = Department("Research")
    val sales = Department("Sales")

    val clark = Employee("Clark", accounting, "New York", 29400.0, "Manager")
    val king = Employee("King", accounting, "New York", 60000.0, "President")
    val miller = Employee("Miller", accounting, "New York", 15600.0, "Clerk")
    val smith = Employee("Smith", research, "New York", 11400.0, "Clerk")
    val adams = Employee("Adams", research, "New York", 11400.0, "Clerk")
    val ford = Employee("Ford", research, "New York", 36000.0, "Analyst")
    val scott = Employee("Scott", research, "New York", 36000.0, "Analyst")
    val jones = Employee("Jones", research, "New York", 35700.0, "Manager")
    val allen = Employee("Allen", sales, "New York", 16800.0, "Salesman")
    val blake = Employee("Blake", sales, "New York", 34200.0, "Manager")
    val martin = Employee("Martin", sales, "New York", 16800.0, "Salesman")
    val james = Employee("James", sales, "New York", 11400.0, "Clerk")
    val turner = Employee("Turner", sales, "New York", 16800.0, "Salesman")
    val ward = Employee("Ward", sales, "New York", 16800.0, "Salesman")

    employees.add(clark)
    employees.add(king)
    employees.add(miller)
    employees.add(smith)
    employees.add(adams)
    employees.add(ford)
    employees.add(scott)
    employees.add(jones)
    employees.add(allen)
    employees.add(blake)
    employees.add(martin)
    employees.add(james)
    employees.add(turner)
    employees.add(ward)

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
  }

  // Query

  val format = NumberFormat.getCurrencyInstance()

  val avg = for (employees <- obj) yield {
    val n = employees.size
    val s = employees.iterator.map(_.salary).sum
    Employee("", Department(""), null, s / n , null)
  }

  println(avg.get.salary)

  (for (o <- Lazy(obj); fmt <- Lazy(format)) yield {
    for (average <- avg; employees <- o) yield {
      for (e <- employees.iterator if e.salary < average.salary) println(e + " " + fmt.format(e.salary))

      val bySalary: SortedSet[Employee] = new TreeSet[Employee](SalaryOrdering)
      bySalary.addAll(employees)

      println()

      for (e <- bySalary.tailSet(average).iterator) println(e + " " + fmt.format(e.salary))
    }
  }).get
}
