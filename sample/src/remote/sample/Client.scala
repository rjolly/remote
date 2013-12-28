package remote.sample

import Sample.{populate, average, query}

object Client extends App {
  populate
  val avg = average
  println(avg.get.salary)
  query(avg)
}
