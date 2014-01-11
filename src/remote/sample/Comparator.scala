package remote.sample

import remote.Remote

object Comparator {
  def bySalary(implicit context: Remote[Company]) = for (obj <- context) yield SalaryOrdering

  implicit class Stub[T](val value: Remote[java.util.Comparator[T]]) extends Remote.Stub[java.util.Comparator[T]] with java.util.Comparator[Remote[T]] {
    def compare(o1: Remote[T], o2: Remote[T]) = (for (obj <- value; obj1 <- o1; obj2 <- o2) yield obj.compare(obj1, obj2)).get
  }
}
