package remote.sample

import remote.Remote

object Iterator {
  implicit class Stub[T](val value: Remote[java.util.Iterator[T]]) extends Remote.Stub[java.util.Iterator[T]] with java.util.Iterator[Remote[T]] {
    def hasNext = (for (obj <- value) yield obj.hasNext).get
    def next = for (obj <- value) yield obj.next
    def remove = (for (obj <- value) yield obj.remove).get
  }
}
