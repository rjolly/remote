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
    def remove(o: Object) = ???
    def containsAll(c: java.util.Collection[_]) = ???
    def addAll(c: java.util.Collection[_ <: Remote[E]]) = ???
    def removeAll(c: java.util.Collection[_]) = ???
    def retainAll(c: java.util.Collection[_]) = ???
    def remove(e: Remote[E]) = (for (obj <- value; elem <- e) yield obj.remove(elem)).get
    def containsAll(c: Stub[E]) = (for (co <- Lazy(c.value)) yield {
      (for (obj <- value; coll <- co) yield obj.containsAll(coll)).get
    }).get
    def addAll(c: Stub[E]) = (for (co <- Lazy(c.value)) yield {
      (for (obj <- value; coll <- co) yield obj.addAll(coll)).get
    }).get
    def removeAll(c: Stub[E]) = (for (co <- Lazy(c.value)) yield {
      (for (obj <- value; coll <- co) yield obj.removeAll(coll)).get
    }).get
    def retainAll(c: Stub[E]) = (for (co <- Lazy(c.value)) yield {
      (for (obj <- value; coll <- co) yield obj.retainAll(coll)).get
    }).get
    def clear = (for (obj <- value) yield obj.clear).get
  }
}
