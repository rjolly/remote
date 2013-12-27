package remote.sample

import remote.Remote
import java.text.NumberFormat
import java.util.{Collection, SortedSet, TreeSet}
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.iterableAsScalaIterable

object Sample extends App {
  val obj = Remote.lookup[Collection[Employee]]("obj")

  // Populate

  for (employees <- obj) yield {
    val accounting = Department("Accounting")
    val research = Department("Research")
    val sales = Department("Sales")

    val clark = Employee("Clark", accounting, "New York", 29400.0, null, "Manager")
    val king = Employee("King", accounting, "New York", 60000.0, null, "President")
    val miller = Employee("Miller", accounting, "New York", 15600.0, null, "Clerk")
    val smith = Employee("Smith", research, "New York", 11400.0, null, "Clerk")
    val adams = Employee("Adams", research, "New York", 11400.0, null, "Clerk")
    val ford = Employee("Ford", research, "New York", 36000.0, null, "Analyst")
    val scott = Employee("Scott", research, "New York", 36000.0, null, "Analyst")
    val jones = Employee("Jones", research, "New York", 35700.0, null, "Manager")
    val allen = Employee("Allen", sales, "New York", 16800.0, null, "Salesman")
    val blake = Employee("Blake", sales, "New York", 34200.0, null, "Manager")
    val martin = Employee("Martin", sales, "New York", 16800.0, null, "Salesman")
    val james = Employee("James", sales, "New York", 11400.0, null, "Clerk")
    val turner = Employee("Turner", sales, "New York", 16800.0, null, "Salesman")
    val ward = Employee("Ward", sales, "New York", 16800.0, null, "Salesman")

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

    List(clark, king, miller, smith, adams, ford, scott, jones, allen, blake, martin, james, turner, ward) foreach {
      employees.add(_)
    }
  }

  // Query

  val format = NumberFormat.getCurrencyInstance()

  val avg = for (employees <- obj) yield {
    val n = employees.size
    val s = employees.map(_.salary).sum
    Employee(name = "", salary = s / n)
  }

  (for (emp <- Lazy(obj); fmt <- Lazy(format)) yield {
    for (average <- avg; employees <- emp) yield {
      for (e <- employees if e.salary < average.salary) println(e + " " + fmt.format(e.salary))

      val bySalary: SortedSet[Employee] = new TreeSet[Employee](SalaryOrdering)
      bySalary.addAll(employees)

      println()

      for (e <- bySalary.tailSet(average)) println(e + " " + fmt.format(e.salary))
    }
  }).get
}
