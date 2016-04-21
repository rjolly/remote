package remote.sample

import remote.Remote
import Implicits.remote2iterator

object Collection {
  implicit class Stub[E](val value: Remote[java.util.Collection[E]]) extends Remote.Stub[java.util.Collection[E]] with StubLike[E]

  trait StubLike[E] extends java.util.Collection[Remote[E]] {
    def value: Remote[java.util.Collection[E]]
    def size = (for (obj <- value) yield obj.size).get
    def isEmpty = (for (obj <- value) yield obj.isEmpty).get
    def contains(o: Object) = (for (obj <- value) yield obj.contains(o)).get
    def iterator = for (obj <- value) yield obj.iterator
    def toArray = (for (obj <- value) yield obj.toArray).get
    def toArray[A](a: Array[A with Object]) = (for (obj <- value) yield obj.toArray(a)).get
    def add(e: Remote[E]) = (for (obj <- value; elem <- e) yield obj.add(elem)).get
    def remove(o: Object) = o match {
      case e: Remote[E] => (for (obj <- value; elem <- e) yield obj.remove(elem)).get
    }
    def containsAll(c: java.util.Collection[_]) = c match {
      case c: Stub[E] => {
        val co = c.value
        (for (obj <- value; coll <- co) yield obj.containsAll(coll)).get
      }
    }
    def addAll(c: java.util.Collection[_ <: Remote[E]]) = c match {
      case c: Stub[E] => {
        val co = c.value
        (for (obj <- value; coll <- co) yield obj.addAll(coll)).get
      }
    }
    def removeAll(c: java.util.Collection[_]) = c match {
      case c: Stub[E] => {
        val co = c.value
        (for (obj <- value; coll <- co) yield obj.removeAll(coll)).get
      }
    }
    def retainAll(c: java.util.Collection[_]) = c match {
      case c: Stub[E] => {
        val co = c.value
        (for (obj <- value; coll <- co) yield obj.retainAll(coll)).get
      }
    }
    def clear = (for (obj <- value) yield obj.clear).get
  }
}
