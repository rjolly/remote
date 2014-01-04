package remote.sample

import Sample.{populate, average, query, select, setManager, setSalary}

object Client extends App {
  populate

  val avg = average
  println(avg.get.salary)
  query(avg)

  val miller = select("Miller")
  assert(miller.get.manager.name == "Clark")
  assert(miller.get.manager.salary == 29400.0)

  val turner = select("Turner")
  assert(turner.get.salary == 16800.0)

  setManager(miller, turner)
  assert(miller.get.manager.name == "Turner")
  assert(miller.get.manager.salary == 16800.0)

  setSalary(turner, 10000.0)
  assert(turner.get.salary == 10000.0)
  assert(miller.get.manager.salary == 10000.0)
}
