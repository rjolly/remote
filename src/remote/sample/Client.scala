package remote.sample

import Sample.{populate, average, query, select, setManager, setSalary}
import scala.collection.convert.WrapAsScala.iterableAsScalaIterable

object Client extends App {
  populate

  val avg = average
  println(avg.get.salary)
  query(avg)

  val miller = select("Miller")
  assert (miller.get.manager.name == "Clark")
  assert (miller.get.manager.salary == 29400.0)

  val turner = select("Turner")
  assert (turner.get.salary == 16800.0)

  setManager(miller, turner)
  assert (miller.get.manager.name == "Turner")
  assert (miller.get.manager.salary == 16800.0)

  setSalary(turner, 10000.0)
  assert (turner.get.salary == 10000.0)
  assert (miller.get.manager.salary == 10000.0)

  val emps = Collection.Stub(Sample.obj.employees)
  val iterator = emps.iterator
  val clark = iterator.next
  val king = iterator.next
  assert (clark.name == "Clark")
  assert (king.name == "King")
  assert (clark.salary == 29400.0)
  assert (king.salary == 60000.0)
  king.salary = 50000.0
  assert (king.salary == 50000.0)
  assert (clark.manager.name == "King")
  clark.manager = clark
  clark.salary = 29000.0
  assert (clark.manager.name == "Clark")
  assert (clark.manager.salary == 29000.0)
}
