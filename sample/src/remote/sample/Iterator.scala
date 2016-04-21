package remote.sample

import remote.Remote

object Iterator {
  implicit class Stub[E](val value: Remote[java.util.Iterator[E]]) extends Remote.Stub[java.util.Iterator[E]] with java.util.Iterator[Remote[E]] {
    def hasNext = (for (obj <- value) yield obj.hasNext).get
    def next = for (obj <- value) yield obj.next
    def remove = (for (obj <- value) yield obj.remove).get
  }
}
