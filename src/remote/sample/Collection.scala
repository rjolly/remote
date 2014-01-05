package remote.sample

import remote.Remote

object Collection {
  implicit class Stub[T](val value: Remote[java.util.Collection[T]]) extends Remote.Stub[java.util.Collection[T]] with java.util.Collection[Remote[T]] {
    def size = (for (obj <- value) yield obj.size).get
    def isEmpty = (for (obj <- value) yield obj.isEmpty).get
    def contains(o: Object) = (for (obj <- value) yield obj.contains(o)).get
    def iterator = Iterator.Stub(for (obj <- value) yield obj.iterator)
    def toArray = (for (obj <- value) yield obj.toArray).get
    def toArray[A](a: Array[A with Object]) = (for (obj <- value) yield obj.toArray(a)).get
    def add(e: Remote[T]) = (for (obj <- value; elem <- e) yield obj.add(elem)).get
    def remove(o: Object) = (for (obj <- value) yield obj.remove(o)).get
    def containsAll(c: java.util.Collection[_]) = ???
    def addAll(c: java.util.Collection[_ <: Remote[T]]) = ???
    def removeAll(c: java.util.Collection[_]) = ???
    def retainAll(c: java.util.Collection[_]) = ???
    def clear = (for (obj <- value) yield obj.clear).get
  }
}
